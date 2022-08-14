package thien.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import thien.dto.BaseRequestDto;
import thien.dto.BaseResponseDto;
import thien.dto.loainhanvien.CreateLoaiNhanVienDto;
import thien.dto.loainhanvien.UpdateLoaiNhanVienDto;
import thien.entities.Khoa;
import thien.entities.LoaiNhanVien;
import thien.repository.LoaiNhanVienRepository;
import thien.util.core.QuerySpecific;
import thien.util.core.SearchSpecific;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@Log4j2
public class LoaiNhanVienService {
    @Autowired
    LoaiNhanVienRepository repository;

    @Autowired
    UtilService utilService;

    public BaseResponseDto<LoaiNhanVien> find(BaseRequestDto formData) throws ExecutionException, InterruptedException {
        try {
            CompletableFuture<List<LoaiNhanVien>> list = this.list(formData);
            CompletableFuture<Integer> count = this.count();
            CompletableFuture.allOf(list, count).join();
            return new BaseResponseDto<LoaiNhanVien>(formData.getPageIndex(), formData.getPageSize(), count.get(), list.get());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    public LoaiNhanVien findById(Integer id) {
        try {
            return repository.findById(id).get();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    public BaseResponseDto<LoaiNhanVien> search(BaseRequestDto formData) throws ExecutionException, InterruptedException {
        try {
            CompletableFuture<List<LoaiNhanVien>> list = this.like(formData);
            CompletableFuture<Integer> count = this.count();
            CompletableFuture.allOf(list, count).join();
            return new BaseResponseDto<LoaiNhanVien>(formData.getPageIndex(), formData.getPageSize(), count.get(), list.get());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    public LoaiNhanVien create(CreateLoaiNhanVienDto formData) {
        try {
            return repository.save(formData.toEntity());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    public LoaiNhanVien update(Integer id, UpdateLoaiNhanVienDto formData) {
        try {
            LoaiNhanVien newEntity = repository.save(formData.toEntity());
            return newEntity;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    public void delete(Integer id) {
        try {
            repository.deleteById(id);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @Async
    public CompletableFuture<List<LoaiNhanVien>> list(BaseRequestDto formData) {
        QuerySpecific querySpecific = new QuerySpecific<LoaiNhanVien>(formData.getQuery());
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
                (int) repository.count()
        );
    }

    @Async
    public CompletableFuture<List<LoaiNhanVien>> like(BaseRequestDto formData) {
        SearchSpecific searchSpecific = new SearchSpecific<LoaiNhanVien>(formData.getQuery());
        return CompletableFuture.completedFuture(
                this.repository.findAll(
                        searchSpecific,
                        this.utilService.paginate(formData)
                ).getContent()
        );
    }
}
