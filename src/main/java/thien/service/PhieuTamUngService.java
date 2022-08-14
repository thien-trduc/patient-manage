package thien.service;

import com.google.gson.JsonElement;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import thien.dto.BaseRequestDto;
import thien.dto.BaseResponseDto;
import thien.dto.ThongKeBenhNhanPhongDto;
import thien.dto.phieutamung.CreatePhieuTamUngDto;
import thien.dto.phieutamung.UpdatePhieuTamUngDto;
import thien.entities.PhieuTamUng;
import thien.repository.PhieuTamUngRepository;
import thien.util.core.QuerySpecific;
import thien.util.core.SearchSpecific;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@Log4j2
public class PhieuTamUngService {
    PhieuTamUngRepository repository;
    UtilService utilService;
    EntityManager entityManager;

    @Autowired
    public PhieuTamUngService(PhieuTamUngRepository repository, UtilService utilService, EntityManager entityManager) {
        this.repository = repository;
        this.utilService = utilService;
        this.entityManager = entityManager;
    }

    public BaseResponseDto<PhieuTamUng> find(BaseRequestDto formData) throws ExecutionException, InterruptedException {
        try {
            String maBA = null;
            JsonElement elementBenhAn = utilService.valueByKeyJson(formData.getQuery(), "benhAn");
            if (elementBenhAn != null) {
                maBA = elementBenhAn.getAsJsonObject().get("maBA").getAsString();
            }
            Pageable pageable = utilService.paginate(formData);
            CompletableFuture<List<PhieuTamUng>> list = list(maBA,pageable);
            CompletableFuture<Integer> count = count(maBA);
            CompletableFuture.allOf(list, count).join();
            return new BaseResponseDto<>(formData.getPageIndex(), formData.getPageSize(), count.get(), list.get());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    public PhieuTamUng findById(String id) {
        try {
            return repository.findById(id).get();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

//    public BaseResponseDto<PhieuTamUng> search(BaseRequestDto formData) throws ExecutionException, InterruptedException {
//        try {
//            CompletableFuture<List<PhieuTamUng>> list = like(formData);
//            CompletableFuture<Integer> count = countLike(formData);
//            CompletableFuture.allOf(list, count).join();
//            return new BaseResponseDto<PhieuTamUng>(formData.getPageIndex(), formData.getPageSize(), count.get(), list.get());
//        } catch (Exception e) {
//            log.error(e.getMessage());
//            throw e;
//        }
//    }

    public PhieuTamUng create(CreatePhieuTamUngDto formData) {
        try {
            return repository.save(formData.toEntity());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    public PhieuTamUng update(String id, UpdatePhieuTamUngDto formData) {
        try {
            PhieuTamUng oldEntity = repository.findById(id).get();
            PhieuTamUng newEntity = formData.toEntityUpdate(oldEntity);
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

    public float tinhPhanTramTamUng(String maBA,Float giaTri) {
        Query query = entityManager.createNativeQuery("select * from sp_tinh_muc_tam_ung(:mabenhan,:phantram)");
        query.setParameter("mabenhan",maBA);
        query.setParameter("phantram", giaTri);

        List<Object> result = (List<Object>) query.getResultList();
        Iterator itr = result.iterator();
        if (itr.hasNext()) {
            Double res = (Double) itr.next();
            return Float.valueOf(res.toString());
        }
        return 0;
    }

    @Async
    public CompletableFuture<List<PhieuTamUng>> list(String maBA, Pageable pageable) {
        if (maBA !=null) {
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

//    @Async
//    public CompletableFuture<List<PhieuTamUng>> like(BaseRequestDto formData) {
//        SearchSpecific searchSpecific = new SearchSpecific<PhieuTamUng>(formData.getQuery());
//        return CompletableFuture.completedFuture(
//                this.repository.findAll(
//                        searchSpecific,
//                        utilService.paginate(formData)
//                ).getContent()
//        );
//    }
//
//    @Async
//    public CompletableFuture<Integer> countLike(BaseRequestDto formData) {
//        SearchSpecific searchSpecific = new SearchSpecific<PhieuTamUng>(formData.getQuery());
//        return CompletableFuture.completedFuture(
//                (int) repository.count(searchSpecific)
//        );
//    }
}
