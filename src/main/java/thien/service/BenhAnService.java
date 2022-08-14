package thien.service;

import com.google.gson.JsonObject;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import thien.dto.BaseRequestDto;
import thien.dto.BaseResponseDto;
import thien.dto.benhan.CreateBenhAnDto;
import thien.dto.benhan.CreateBenhAnKhamDto;
import thien.dto.benhan.UpdateBenhAnDto;
import thien.entities.BenhAn;
import thien.entities.BenhNhan;
import thien.repository.BenhAnKhamRepository;
import thien.repository.BenhAnRepository;
import thien.repository.BenhAnRepository;
import thien.util.core.QuerySpecific;
import thien.util.core.SearchSpecific;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@Log4j2
public class BenhAnService {
    BenhAnRepository repository;
    BenhAnKhamRepository benhAnKhamRepository;
    UtilService utilService;

    @Autowired
    public BenhAnService(BenhAnRepository repository, BenhAnKhamRepository benhAnKhamRepository, UtilService utilService) {
        this.repository = repository;
        this.benhAnKhamRepository = benhAnKhamRepository;
        this.utilService = utilService;
    }

    public BaseResponseDto<BenhAn> find(BaseRequestDto formData) throws ExecutionException, InterruptedException {
        try {
            CompletableFuture<List<BenhAn>> list = list(formData);
            CompletableFuture<Integer> count = count();
            CompletableFuture.allOf(list, count).join();
            return new BaseResponseDto<BenhAn>(formData.getPageIndex(), formData.getPageSize(), count.get(), list.get());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    public BenhAn findById(String id) {
        try {
            return repository.findById(id).get();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    public BaseResponseDto<BenhAn> searchByCMND(BaseRequestDto formData) throws ExecutionException, InterruptedException {
        try {
            if (formData.getQuery() == null || formData.getQuery().isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
            String cmnd = this.utilService.valueByKeyJson(formData.getQuery(), "benhNhan")
                    .getAsJsonObject()
                    .get("cmnd")
                    .getAsString();
            Pageable pageable = utilService.paginate(formData);
            CompletableFuture<List<BenhAn>> list = findByBenhNhan_CmndContaining(cmnd,pageable);
            CompletableFuture<Integer> count = countByBenhNhan_CmndContaining(cmnd);
            CompletableFuture.allOf(list, count).join();
            return new BaseResponseDto<>(formData.getPageIndex(), formData.getPageSize(), count.get(), list.get());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @Transactional(rollbackFor = {Exception.class, Throwable.class}, isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
    public BenhAn create(CreateBenhAnDto formData) {
        try {
            BenhAn ba = repository.save(formData.toEntity());
            CreateBenhAnKhamDto createBenhAnKhamDto = new CreateBenhAnKhamDto(formData, ba.getMaBA());
            benhAnKhamRepository.save(createBenhAnKhamDto.toEntity());
            return ba;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    public BenhAn update(String id, UpdateBenhAnDto updateBenhAnDto) {
        try {
            BenhAn oldEntity = repository.findById(id).get();
            BenhAn newEntity = updateBenhAnDto.toEntityUpdate(oldEntity);
            return repository.save(newEntity);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    public void delete(String id) {
        try {
            repository.deleteById(id);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @Async
    public CompletableFuture<List<BenhAn>> list(BaseRequestDto formData) {
        QuerySpecific<BenhAn> querySpecific = new QuerySpecific<>(formData.getQuery());
        return CompletableFuture.completedFuture(
                repository.findAll(
                        querySpecific,
                        utilService.paginate(formData)
                ).getContent()
        );
    }

    @Async
    public CompletableFuture<Integer> count() {
        return CompletableFuture.completedFuture(
                (int) repository.count()
        );
    }

    @Async
    public CompletableFuture<List<BenhAn>> findByBenhNhan_CmndContaining(String cmnd, Pageable pageable) {

        return CompletableFuture.completedFuture(
                repository.findByBenhNhan_CmndContaining(
                        cmnd,
                        pageable
                )
        );
    }

    @Async
    public CompletableFuture<Integer> countByBenhNhan_CmndContaining(String cmnd) {
        return CompletableFuture.completedFuture(
                repository.countByBenhNhan_CmndContaining(
                        cmnd
                )
        );
    }


}
