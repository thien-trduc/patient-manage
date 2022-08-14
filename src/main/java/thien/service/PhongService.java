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
import thien.dto.phong.CreateGiaPhongDto;
import thien.dto.phong.CreatePhongDto;
import thien.dto.phong.PhongDto;
import thien.dto.phong.UpdatePhongDto;
import thien.entities.Phong;
import thien.entities.PhongCapNhatGia;
import thien.repository.PhongCapNhatGiaRepository;
import thien.repository.PhongRepository;
import thien.util.core.QuerySpecific;
import thien.util.core.SearchSpecific;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
@Log4j2
public class PhongService {
    private PhongRepository repository;
    private PhongCapNhatGiaRepository giaRepository;
    private UtilService utilService;

    @Autowired
    public PhongService(PhongRepository repository, PhongCapNhatGiaRepository giaRepository, UtilService utilService) {
        this.repository = repository;
        this.giaRepository = giaRepository;
        this.utilService = utilService;
    }

    public BaseResponseDto<PhongDto> find(BaseRequestDto formData) throws ExecutionException, InterruptedException {
        try {
            Pageable pageable = utilService.paginate(formData);
            QuerySpecific<Phong> querySpecific = new QuerySpecific<>(formData.getQuery());
            CompletableFuture<List<PhongDto>> list = list(querySpecific, pageable);
            CompletableFuture<Integer> count = count(querySpecific);
            CompletableFuture.allOf(list, count).join();
            return new BaseResponseDto<>(formData.getPageIndex(), formData.getPageSize(), count.get(), list.get());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    public BaseResponseDto<PhongDto> search(BaseRequestDto formData) throws ExecutionException, InterruptedException {
        try {
            Pageable pageable = utilService.paginate(formData);
            SearchSpecific<Phong> searchSpecific = new SearchSpecific<>(formData.getQuery());
            CompletableFuture<List<PhongDto>> list = like(searchSpecific, pageable);
            CompletableFuture<Integer> count = countLike(searchSpecific);
            CompletableFuture.allOf(list, count).join();
            return new BaseResponseDto<>(formData.getPageIndex(), formData.getPageSize(), count.get(), list.get());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }


    public PhongDto findById(Integer id) {
        try {
            Phong phong = repository.findById(id).get();
            return getPhongAndGia(phong);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @Transactional(rollbackFor = {Exception.class, Throwable.class}, isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
    public Phong create(CreatePhongDto formData) {
        try {
            Phong phong = repository.save(formData.toEntity());
            CreateGiaPhongDto createGiaPhongDto = new CreateGiaPhongDto(formData, new Date());
            PhongCapNhatGia PhongCapNhatGia = createGiaPhongDto.toEntity(phong.getMaPhong());
            giaRepository.save(PhongCapNhatGia);
            return phong;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    public Phong update(Integer id, UpdatePhongDto updatePhongDto) {
        try {
            Phong oldEntity = repository.findById(id).get();
            Phong newEntity = updatePhongDto.toEntityUpdate(oldEntity);
            return repository.save(newEntity);
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

    public BaseResponseDto<PhongCapNhatGia> findGias(Integer maPhong, BaseRequestDto formData) throws ExecutionException, InterruptedException {
        try {
            Pageable pageable = utilService.paginate(formData);
            CompletableFuture<List<PhongCapNhatGia>> list = listGia(maPhong, pageable);
            CompletableFuture<Integer> count = countGia(maPhong);
            CompletableFuture.allOf(list, count).join();
            return new BaseResponseDto<>(formData.getPageIndex(), formData.getPageSize(), count.get(), list.get());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    public PhongCapNhatGia createGia(Integer maPhong, CreateGiaPhongDto formData) {
        try {
            return giaRepository.save(formData.toEntity(maPhong));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    public void deleteGia(Integer maPhong, Date ngayCapNhat) {
        try {
            giaRepository.deleteByNgayCapNhatAndPhong_MaPhong(ngayCapNhat, maPhong);
        }catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @Async
    public CompletableFuture<List<PhongDto>> list(QuerySpecific<Phong> querySpecific, Pageable pageable) {
        return CompletableFuture.completedFuture(
               repository.findAll(querySpecific, pageable).getContent()
                        .parallelStream()
                        .map(this::getPhongAndGia)
                        .collect(Collectors.toList())
        );
    }

    @Async
    public CompletableFuture<Integer> count(QuerySpecific<Phong> querySpecific) {
        return CompletableFuture.completedFuture(
                (int) repository.count(querySpecific)
        );
    }

    @Async
    public CompletableFuture<List<PhongDto>> like(SearchSpecific<Phong> searchSpecific, Pageable pageable) {
        return CompletableFuture.completedFuture(
                repository.findAll(searchSpecific, pageable).getContent()
                        .parallelStream()
                        .map(this::getPhongAndGia)
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
    public CompletableFuture<List<PhongCapNhatGia>> listGia(Integer maPhong, Pageable pageable) {
        return CompletableFuture.completedFuture(
                giaRepository.findByPhong_MaPhong(maPhong, pageable)
        );
    }

    @Async
    public CompletableFuture<Integer> countGia(Integer maPhong) {
        return CompletableFuture.completedFuture(
                giaRepository.countByPhong_MaPhong(maPhong)
        );
    }

    public PhongDto getPhongAndGia(Phong entity) {
        Integer maPhong = entity.getMaPhong();
        Date current = new Date();
        Float gia = repository.getGiaPhong(current, maPhong);
        return new PhongDto(entity, gia);
    }
}
