package thien.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import thien.dto.BaseRequestDto;
import thien.dto.BaseResponseDto;
import thien.dto.khoa.CreateKhoaDto;
import thien.dto.khoa.UpdateKhoaDto;
import thien.entities.Khoa;
import thien.repository.KhoaRepository;
import thien.util.core.QuerySpecific;
import thien.util.core.SearchSpecific;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@Log4j2
public class KhoaService {
    @Autowired
    KhoaRepository repository;
    @Autowired
    UtilService utilService;

    public BaseResponseDto<Khoa> find(BaseRequestDto formData) throws ExecutionException, InterruptedException {
        try {
            log.info(formData.getSort());
            CompletableFuture<List<Khoa>> list = this.list(formData);
            CompletableFuture<Integer> count = this.count();
            CompletableFuture.allOf(list, count).join();
            return new BaseResponseDto<Khoa>(formData.getPageIndex(), formData.getPageSize(), count.get(), list.get());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    public Khoa findById(String id) {
        try {
            return this.repository.findById(id).get();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    public BaseResponseDto<Khoa> search(BaseRequestDto formData) throws ExecutionException, InterruptedException {
        try {
            CompletableFuture<List<Khoa>> list = this.like(formData);
            CompletableFuture<Integer> count = this.count();
            CompletableFuture.allOf(list, count).join();
            return new BaseResponseDto<Khoa>(formData.getPageIndex(), formData.getPageSize(), count.get(), list.get());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    public Khoa create(CreateKhoaDto formData) {
        try {
            return this.repository.save(formData.toEntity());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @Transactional
    public Khoa update(String id, UpdateKhoaDto updateKhoaDto) {
        try {
            this.repository.deleteById(id);
            Khoa khoa = updateKhoaDto.toEntity();
            khoa.setMaKhoa(id);
            Khoa newEntity = this.repository.save(khoa);
            return newEntity;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    public void delete(String id) {
        try {
            this.repository.deleteById(id);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @Async
    public CompletableFuture<List<Khoa>> list(BaseRequestDto formData) {
        QuerySpecific querySpecific = new QuerySpecific<Khoa>(formData.getQuery());
        return CompletableFuture.completedFuture(
                this.repository.findAll(
                        querySpecific,
                        this.utilService.paginate(formData)
                ).getContent()
        );
    }

    @Async
    public CompletableFuture<Integer> count() {
        return CompletableFuture.completedFuture(
                (int) this.repository.count()
        );
    }

    @Async
    public CompletableFuture<List<Khoa>> like(BaseRequestDto formData) {
        SearchSpecific searchSpecific = new SearchSpecific<Khoa>(formData.getQuery());
        return CompletableFuture.completedFuture(
                this.repository.findAll(
                        searchSpecific,
                        this.utilService.paginate(formData)
                ).getContent()
        );
    }
}
