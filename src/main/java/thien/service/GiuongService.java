package thien.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import thien.dto.BaseRequestDto;
import thien.dto.BaseResponseDto;
import thien.dto.giuong.CreateGiuongDto;
import thien.dto.giuong.UpdateGiuongDto;
import thien.entities.Giuong;
import thien.entities.Khoa;
import thien.repository.GiuongRepository;
import thien.util.core.QuerySpecific;
import thien.util.core.SearchSpecific;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@Log4j2
public class GiuongService {

    private GiuongRepository repository;
    private UtilService utilService;

    @Autowired
    public GiuongService(GiuongRepository repository, UtilService utilService) {
        this.repository = repository;
        this.utilService = utilService;
    }

    public BaseResponseDto<Giuong> find(BaseRequestDto formData) throws ExecutionException, InterruptedException {
        try {
            System.out.println("Vao Day");
            QuerySpecific<Giuong>  querySpecific = new QuerySpecific<>(formData.getQuery());
            Pageable pageable = utilService.paginate(formData);
            CompletableFuture<List<Giuong>> list = this.list(querySpecific,pageable);
            CompletableFuture<Integer> count = this.count(querySpecific);
            CompletableFuture.allOf(list, count).join();
            return new BaseResponseDto<>(formData.getPageIndex(), formData.getPageSize(), count.get(), list.get());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    public Giuong findById(Integer id) {
        try {
            return repository.findById(id).get();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    public BaseResponseDto<Giuong> search(BaseRequestDto formData) throws ExecutionException, InterruptedException {
        try {
            SearchSpecific<Giuong> searchSpecific = new SearchSpecific<>(formData.getQuery());
            Pageable pageable = utilService.paginate(formData);
            CompletableFuture<List<Giuong>> list = this.like(searchSpecific,pageable);
            CompletableFuture<Integer> count = this.countLike(searchSpecific);
            CompletableFuture.allOf(list, count).join();
            return new BaseResponseDto<>(formData.getPageIndex(), formData.getPageSize(), count.get(), list.get());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    public Giuong create(CreateGiuongDto formData) {
        try {
            return this.repository.save(formData.toEntity());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }
    
    public Giuong update(Integer id, UpdateGiuongDto updateGiuongDto) {
        try {
            Giuong oldEntity = repository.findById(id).get();
            return repository.save(updateGiuongDto.toUpdateEntity(oldEntity));
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
    public CompletableFuture<List<Giuong>> list(QuerySpecific<Giuong> querySpecific, Pageable pageable) {
        return CompletableFuture.completedFuture(
                repository.findAll(
                        querySpecific,
                        pageable
                ).getContent()
        );
    }

    @Async
    public CompletableFuture<Integer> count(QuerySpecific<Giuong> querySpecific) {
        return CompletableFuture.completedFuture(
                (int) repository.count(querySpecific)
        );
    }

    @Async
    public CompletableFuture<List<Giuong>> like(SearchSpecific<Giuong> searchSpecific, Pageable pageable ) {
        return CompletableFuture.completedFuture(
                repository.findAll(
                        searchSpecific,
                        pageable
                ).getContent()
        );
    }

    @Async
    public CompletableFuture<Integer> countLike(SearchSpecific<Giuong>  searchSpecific) {
        return CompletableFuture.completedFuture(
                (int) repository.count(searchSpecific)
        );
    }
}
