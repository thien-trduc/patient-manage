package thien.service;

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
import thien.dto.thuoc.CreateGiaThuocDto;
import thien.dto.thuoc.CreateThuocDto;
import thien.dto.thuoc.ThuocDto;
import thien.dto.thuoc.UpdateThuocDto;
import thien.entities.Thuoc;
import thien.entities.ThuocCapNhatGia;
import thien.repository.ThuocCapNhatGiaRepository;
import thien.repository.ThuocRepository;
import thien.repository.ThuocRepository;
import thien.util.core.QuerySpecific;
import thien.util.core.SearchSpecific;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
@Log4j2
public class ThuocService {
    @Autowired
    ThuocRepository repository;

    @Autowired
    ThuocCapNhatGiaRepository giaRepository;

    @Autowired
    UtilService utilService;

    public BaseResponseDto<ThuocDto> find(BaseRequestDto formData) throws ExecutionException, InterruptedException {
        try {
            Pageable pageable = utilService.paginate(formData);
            QuerySpecific querySpecific = new QuerySpecific<Thuoc>(formData.getQuery());
            CompletableFuture<List<ThuocDto>> list = list(querySpecific, pageable);
            CompletableFuture<Integer> count = count(querySpecific);
            CompletableFuture.allOf(list, count).join();
            return new BaseResponseDto<>(formData.getPageIndex(), formData.getPageSize(), count.get(), list.get());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    public BaseResponseDto<ThuocDto> search(BaseRequestDto formData) throws ExecutionException, InterruptedException {
        try {
            Pageable pageable = utilService.paginate(formData);
            SearchSpecific searchSpecific = new SearchSpecific<Thuoc>(formData.getQuery());
            CompletableFuture<List<ThuocDto>> list = like(searchSpecific, pageable);
            CompletableFuture<Integer> count = countLike(searchSpecific);
            CompletableFuture.allOf(list, count).join();
            return new BaseResponseDto<>(formData.getPageIndex(), formData.getPageSize(), count.get(), list.get());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }


    public ThuocDto findById(String id) {
        try {
            Thuoc thuoc = repository.findById(id).get();
            return getThuocAndGia(thuoc);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @Transactional(rollbackFor = {Exception.class, Throwable.class}, isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
    public Thuoc create(CreateThuocDto formData) {
        try {
            Thuoc thuoc = repository.save(formData.toEntity());
            CreateGiaThuocDto createGiaThuocDto = new CreateGiaThuocDto(formData, new Date());
            ThuocCapNhatGia thuocCapNhatGia = createGiaThuocDto.toEntity(thuoc.getMaThuoc());
            giaRepository.save(thuocCapNhatGia);
            return thuoc;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    public Thuoc update(String id, UpdateThuocDto updateThuocDto) {
        try {
            Thuoc oldEntity = repository.findById(id).get();
            Thuoc newEntity = updateThuocDto.toEntityUpdate(oldEntity);
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

    public ThuocDto getThuocAndGia(Thuoc entity) {
        String maThuoc = entity.getMaThuoc();
        Date current = new Date();
        Float gia = repository.getGiaThuoc(current, maThuoc);
        return new ThuocDto(entity, gia);
    }

    public BaseResponseDto<ThuocCapNhatGia> findGias(String maThuoc, BaseRequestDto formData) throws ExecutionException, InterruptedException {
        try {
            Pageable pageable = utilService.paginate(formData);
            CompletableFuture<List<ThuocCapNhatGia>> list = listGia(maThuoc, pageable);
            CompletableFuture<Integer> count = countGia(maThuoc);
            CompletableFuture.allOf(list, count).join();
            return new BaseResponseDto<>(formData.getPageIndex(), formData.getPageSize(), count.get(), list.get());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    public ThuocCapNhatGia createGia(String maThuoc, CreateGiaThuocDto formData) {
        try {
            return giaRepository.save(formData.toEntity(maThuoc));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    public void deleteGia(String maThuoc, Date ngayCapNhat) {
        try {
            giaRepository.deleteByNgayCapNhatAndThuoc_MaThuoc(ngayCapNhat, maThuoc);
        }catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @Async
    public CompletableFuture<List<ThuocDto>> list(QuerySpecific querySpecific, Pageable pageable) {
        return CompletableFuture.completedFuture(
                ((List<Thuoc>) repository.findAll(querySpecific, pageable).getContent())
                        .parallelStream()
                        .map(thuoc -> getThuocAndGia(thuoc))
                        .collect(Collectors.toList())
        );
    }

    @Async
    public CompletableFuture<Integer> count(QuerySpecific querySpecific) {
        return CompletableFuture.completedFuture(
                (int) repository.count(querySpecific)
        );
    }

    @Async
    public CompletableFuture<List<ThuocDto>> like(SearchSpecific searchSpecific, Pageable pageable) {
        return CompletableFuture.completedFuture(
                ((List<Thuoc>) repository.findAll(searchSpecific, pageable).getContent())
                        .parallelStream()
                        .map(thuoc -> getThuocAndGia(thuoc))
                        .collect(Collectors.toList())
        );
    }

    @Async
    public CompletableFuture<Integer> countLike(SearchSpecific searchSpecific) {
        return CompletableFuture.completedFuture(
                (int) repository.count(searchSpecific)
        );
    }

    @Async
    public CompletableFuture<List<ThuocCapNhatGia>> listGia(String maThuoc, Pageable pageable) {
        return CompletableFuture.completedFuture(
                giaRepository.findByThuoc_MaThuoc(maThuoc, pageable)
        );
    }

    @Async
    public CompletableFuture<Integer> countGia(String maThuoc) {
        return CompletableFuture.completedFuture(
                giaRepository.countByThuoc_MaThuoc(maThuoc)
        );
    }

}
