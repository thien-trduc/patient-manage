package thien.service;

import com.google.gson.JsonElement;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import thien.dto.BaseRequestDto;
import thien.dto.BaseResponseDto;
import thien.dto.phieuxuatvien.CreateGiayXuatVienDto;
import thien.dto.phieuxuatvien.UpdateGiayXuatVienDto;
import thien.entities.GiayXuatVien;
import thien.event.domain.SendMailAfterBillEvent;
import thien.repository.GiayXuatVienRepository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@Log4j2
public class GiayXuatVienService {
    private GiayXuatVienRepository repository;
    private UtilService utilService;
    private EntityManager entityManager;

    @Autowired
    public GiayXuatVienService(GiayXuatVienRepository repository, UtilService utilService, EntityManager entityManager) {
        this.repository = repository;
        this.utilService = utilService;
        this.entityManager = entityManager;
    }

    public BaseResponseDto<GiayXuatVien> find(BaseRequestDto formData) throws ExecutionException, InterruptedException {
        String maBA = null;
        JsonElement elementBenhAn = utilService.valueByKeyJson(formData.getQuery(), "benhAn");
        if (elementBenhAn != null) {
            maBA = elementBenhAn.getAsJsonObject().get("maBA").getAsString();
        }
        try {
            Pageable pageable = utilService.paginate(formData);
            CompletableFuture<List<GiayXuatVien>> list = list(maBA, pageable);
            CompletableFuture<Integer> count = count(maBA);
            CompletableFuture.allOf(list, count).join();
            return new BaseResponseDto<>(formData.getPageIndex(), formData.getPageSize(), count.get(), list.get());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    public GiayXuatVien findById(String id) {
        try {
            return repository.findById(id).get();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

//    public BaseResponseDto<GiayXuatVien> search(BaseRequestDto formData) throws ExecutionException, InterruptedException {
//        try {
//            CompletableFuture<List<GiayXuatVien>> list = like(formData);
//            CompletableFuture<Integer> count = countLike(formData);
//            CompletableFuture.allOf(list, count).join();
//            return new BaseResponseDto<GiayXuatVien>(formData.getPageIndex(), formData.getPageSize(), count.get(), list.get());
//        } catch (Exception e) {
//            log.error(e.getMessage());
//            throw e;
//        }
//    }

    public GiayXuatVien create(CreateGiayXuatVienDto formData) {
        try {
            return repository.save(formData.toEntity());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    public GiayXuatVien update(String id, UpdateGiayXuatVienDto formData) {
        try {
            GiayXuatVien oldEntity = repository.findById(id).get();
            GiayXuatVien newEntity = formData.toUpdateEntity(oldEntity);
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
    public CompletableFuture<List<GiayXuatVien>> list(String maBA, Pageable pageable) {
        if (maBA != null) {
            return CompletableFuture.completedFuture(
                    repository.findByBenhAn_MaBA(
                            maBA,
                            pageable
                    )
            );
        }
        return CompletableFuture.completedFuture(
                repository.findAll(
                        pageable
                ).getContent()
        );
    }

    @Async
    public CompletableFuture<Integer> count(String maBA) {
        if (maBA != null) {
            return CompletableFuture.completedFuture(
                    repository.countByBenhAn_MaBA(maBA)
            );
        }
        return CompletableFuture.completedFuture(
                (int) repository.count()
        );
    }
}
