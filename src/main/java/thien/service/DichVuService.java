package thien.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import thien.dto.BaseRequestDto;
import thien.dto.BaseResponseDto;
import thien.dto.dichvu.CreateDichVuDto;
import thien.dto.dichvu.CreateGiaDichVuDto;
import thien.dto.dichvu.DichVuDto;
import thien.dto.dichvu.UpdateDichVuDto;
import thien.entities.DichVu;
import thien.entities.DichVuCapNhatGia;
import thien.repository.DichVuCapNhatGiaRepository;
import thien.repository.DichVuRepository;
import thien.util.core.QuerySpecific;
import thien.util.core.SearchSpecific;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
@Log4j2
public class DichVuService {
    private DichVuRepository repository;
    private DichVuCapNhatGiaRepository giaRepository;
    private UtilService utilService;

    @Autowired
    public DichVuService(DichVuRepository repository, DichVuCapNhatGiaRepository giaRepository, UtilService utilService) {
        this.repository = repository;
        this.giaRepository = giaRepository;
        this.utilService = utilService;
    }

    public BaseResponseDto<DichVuDto> find(BaseRequestDto formData) throws ExecutionException, InterruptedException {
        try {
            Pageable pageable = utilService.paginate(formData);
            QuerySpecific<DichVu> querySpecific = new QuerySpecific<>(formData.getQuery());
            CompletableFuture<List<DichVuDto>> list = list(querySpecific, pageable);
            CompletableFuture<Integer> count = count(querySpecific);
            CompletableFuture.allOf(list, count).join();
            return new BaseResponseDto<>(formData.getPageIndex(), formData.getPageSize(), count.get(), list.get());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    public BaseResponseDto<DichVuDto> search(BaseRequestDto formData) throws ExecutionException, InterruptedException {
        try {
            Pageable pageable = utilService.paginate(formData);
            SearchSpecific<DichVu> searchSpecific = new SearchSpecific<>(formData.getQuery());
            CompletableFuture<List<DichVuDto>> list = like(searchSpecific, pageable);
            CompletableFuture<Integer> count = countLike(searchSpecific);
            CompletableFuture.allOf(list, count).join();
            return new BaseResponseDto<>(formData.getPageIndex(), formData.getPageSize(), count.get(), list.get());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }


    public DichVuDto findById(String id) {
        try {
            DichVu dv = repository.findById(id).get();
            return getDichVuAndGia(dv);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @Transactional(rollbackFor = {Exception.class, Throwable.class}, isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
    public DichVu create(CreateDichVuDto formData) {
        try {
            DichVu dv = repository.save(formData.toEntity());
            CreateGiaDichVuDto createGiaDichVuDto = new CreateGiaDichVuDto(formData, new Date());
            DichVuCapNhatGia dichVuCapNhatGia = createGiaDichVuDto.toEntity(dv.getMaDV());
            giaRepository.save(dichVuCapNhatGia);
            return dv;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    public DichVu update(String id, UpdateDichVuDto updateDichVuDto) {
        try {
            DichVu oldEntity = repository.findById(id).get();
            DichVu newEntity = updateDichVuDto.toEntityUpdate(oldEntity);
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

    public BaseResponseDto<DichVuCapNhatGia> findGias(String maDV, BaseRequestDto formData) throws ExecutionException, InterruptedException {
        try {
            Pageable pageable = utilService.paginate(formData);
            CompletableFuture<List<DichVuCapNhatGia>> list = listGia(maDV, pageable);
            CompletableFuture<Integer> count = countGia(maDV);
            CompletableFuture.allOf(list, count).join();
            return new BaseResponseDto<>(formData.getPageIndex(), formData.getPageSize(), count.get(), list.get());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    public DichVuCapNhatGia createGia(String maDV, CreateGiaDichVuDto formData) {
        try {
            return giaRepository.save(formData.toEntity(maDV));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    public void deleteGia(String maDV, Date ngayCapNhat) {
        try {
            giaRepository.deleteByNgayCapNhatAndDichVu_MaDV(ngayCapNhat, maDV);
        }catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @Async
    public CompletableFuture<List<DichVuDto>> list(QuerySpecific<DichVu>  querySpecific, Pageable pageable) {
        return CompletableFuture.completedFuture(
                ((List<DichVu>) repository.findAll(querySpecific, pageable).getContent())
                        .parallelStream()
                        .map(dv -> getDichVuAndGia(dv))
                        .collect(Collectors.toList())
        );
    }

    @Async
    public CompletableFuture<Integer> count(QuerySpecific<DichVu>  querySpecific) {
        return CompletableFuture.completedFuture(
                (int) repository.count(querySpecific)
        );
    }

    @Async
    public CompletableFuture<List<DichVuDto>> like(SearchSpecific<DichVu>  searchSpecific, Pageable pageable) {
        return CompletableFuture.completedFuture(
                ((List<DichVu>) repository.findAll(searchSpecific, pageable).getContent())
                        .parallelStream()
                        .map(this::getDichVuAndGia)
                        .collect(Collectors.toList())
        );
    }

    @Async
    public CompletableFuture<Integer> countLike(SearchSpecific<DichVu> searchSpecific) {
        return CompletableFuture.completedFuture(
                (int) repository.count(searchSpecific)
        );
    }

    @Async
    public CompletableFuture<List<DichVuCapNhatGia>> listGia(String maDV, Pageable pageable) {
        return CompletableFuture.completedFuture(
                giaRepository.findByDichVu_MaDV(maDV, pageable)
        );
    }

    @Async
    public CompletableFuture<Integer> countGia(String maDV) {
        return CompletableFuture.completedFuture(
                giaRepository.countByDichVu_MaDV(maDV)
        );
    }

    public DichVuDto getDichVuAndGia(DichVu entity) {
        String maDV = entity.getMaDV();
        Date current = new Date();
        Float gia = repository.getGiaDichVu(current, maDV);
        return new DichVuDto(entity, gia);
    }
}
