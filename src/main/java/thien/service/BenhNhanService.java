package thien.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import thien.dto.BaseRequestDto;
import thien.dto.BaseResponseDto;
import thien.dto.benhnhan.CreateBenhNhanDto;
import thien.dto.benhnhan.UpdateBenhNhanDto;
import thien.entities.BenhNhan;
import thien.repository.BenhNhanRepository;
import thien.util.core.QuerySpecific;
import thien.util.core.SearchSpecific;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@Log4j2
public class BenhNhanService {
    @Autowired
    BenhNhanRepository repository;

    @Autowired
    UtilService utilService;


    public BaseResponseDto<BenhNhan> find(BaseRequestDto formData) throws ExecutionException, InterruptedException {
        try {
            CompletableFuture<List<BenhNhan>> list = list(formData);
            CompletableFuture<Integer> count = count(formData);
            CompletableFuture.allOf(list, count).join();
            return new BaseResponseDto<BenhNhan>(formData.getPageIndex(), formData.getPageSize(), count.get(), list.get());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    public BenhNhan findById(String id) {
        try {
            return repository.findById(id).get();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    public BaseResponseDto<BenhNhan> search(BaseRequestDto formData) throws ExecutionException, InterruptedException {
        try {
            CompletableFuture<List<BenhNhan>> list = like(formData);
            CompletableFuture<Integer> count = countLike(formData);
            CompletableFuture.allOf(list, count).join();
            return new BaseResponseDto<BenhNhan>(formData.getPageIndex(), formData.getPageSize(), count.get(), list.get());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    public BenhNhan create(CreateBenhNhanDto formData) {
        try {
            return repository.save(formData.toEntity());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    public BenhNhan update(String id, UpdateBenhNhanDto updateBenhNhanDto) {
        try {
            BenhNhan oldEntity = repository.findById(id).get();
            log.info(oldEntity);
            BenhNhan newEntity = updateBenhNhanDto.toUpdateEntity(oldEntity);
            log.info(newEntity);
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
    public CompletableFuture<List<BenhNhan>> list(BaseRequestDto formData) {
        QuerySpecific querySpecific = new QuerySpecific<BenhNhan>(formData.getQuery());
        return CompletableFuture.completedFuture(
                repository.findAll(
                        querySpecific,
                        utilService.paginate(formData)
                ).getContent()
        );
    }

    @Async
    public CompletableFuture<Integer> count(BaseRequestDto formData) {
        QuerySpecific querySpecific = new QuerySpecific<BenhNhan>(formData.getQuery());
        return CompletableFuture.completedFuture(
                (int) repository.count(querySpecific)
        );
    }

    @Async
    public CompletableFuture<List<BenhNhan>> like(BaseRequestDto formData) {
        SearchSpecific searchSpecific = new SearchSpecific<BenhNhan>(formData.getQuery());
        return CompletableFuture.completedFuture(
                this.repository.findAll(
                        searchSpecific,
                        utilService.paginate(formData)
                ).getContent()
        );
    }

    @Async
    public CompletableFuture<Integer> countLike(BaseRequestDto formData) {
        SearchSpecific searchSpecific = new SearchSpecific<BenhNhan>(formData.getQuery());
        return CompletableFuture.completedFuture(
                (int) repository.count(searchSpecific)
        );
    }
}
