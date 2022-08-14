package thien.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import thien.dto.BaseRequestDto;
import thien.dto.BaseResponseDto;
import thien.dto.hoadon.CreateHoaDonDto;
import thien.dto.hoadon.ThanhToanDto;
import thien.dto.hoadon.UpdateHoaDonDto;
import thien.entities.HoaDon;
import thien.event.domain.SendMailAfterBillEvent;
import thien.repository.HoaDonRepository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@Log4j2
public class HoaDonService {
    private HoaDonRepository repository;
    private BenhAnXepGiuongService benhAnXepGiuongService;
    private UtilService utilService;
    private EntityManager entityManager;
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    public HoaDonService(HoaDonRepository repository, UtilService utilService, EntityManager entityManager, ApplicationEventPublisher eventPublisher, BenhAnXepGiuongService benhAnXepGiuongService) {
        this.repository = repository;
        this.utilService = utilService;
        this.entityManager = entityManager;
        this.eventPublisher = eventPublisher;
        this.benhAnXepGiuongService = benhAnXepGiuongService;
    }

    public BaseResponseDto<HoaDon> find(BaseRequestDto formData) throws ExecutionException, InterruptedException {
        String maBA = null;
        JsonElement elementBenhAn = utilService.valueByKeyJson(formData.getQuery(), "benhAn");
        if (elementBenhAn != null) {
            maBA = elementBenhAn.getAsJsonObject().get("maBA").getAsString();
        }
        try {
            Pageable pageable = utilService.paginate(formData);
            CompletableFuture<List<HoaDon>> list = list(maBA, pageable);
            CompletableFuture<Integer> count = count(maBA);
            CompletableFuture.allOf(list, count).join();
            return new BaseResponseDto<>(formData.getPageIndex(), formData.getPageSize(), count.get(), list.get());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    public HoaDon findById(String id) {
        try {
            return repository.findById(id).get();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

//    public BaseResponseDto<HoaDon> search(BaseRequestDto formData) throws ExecutionException, InterruptedException {
//        try {
//            CompletableFuture<List<HoaDon>> list = like(formData);
//            CompletableFuture<Integer> count = countLike(formData);
//            CompletableFuture.allOf(list, count).join();
//            return new BaseResponseDto<HoaDon>(formData.getPageIndex(), formData.getPageSize(), count.get(), list.get());
//        } catch (Exception e) {
//            log.error(e.getMessage());
//            throw e;
//        }
//    }

    public HoaDon create(CreateHoaDonDto formData) {
        try {
            ThanhToanDto thanhToanDto = ThanhToanVienPhi(formData.getMaBA());
            HoaDon hd = repository.save(formData.toEntity(thanhToanDto));
            benhAnXepGiuongService.traGiuongXuatVien(formData.getMaBA());
            // event mail
            eventPublisher.publishEvent(new SendMailAfterBillEvent(hd.getMaHD()));
            return hd;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    public HoaDon update(String id, UpdateHoaDonDto formData) {
        try {
            HoaDon oldEntity = repository.findById(id).get();
            HoaDon newEntity = formData.toUpdateEntity(oldEntity);
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
    public CompletableFuture<List<HoaDon>> list(String maBA, Pageable pageable) {
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

    public ThanhToanDto ThanhToanVienPhi(String maBA) {
        try {
            ThanhToanDto thanhToanDto = null;
            Query query = entityManager.createNativeQuery("select * from thanh_toan_vien_phi(:maBA)");
            query.setParameter("maBA", maBA);
            List<Object> result = (List<Object>) query.getResultList();
            Iterator itr = result.iterator();
            while (itr.hasNext()) {
                Object[] obj = (Object[]) itr.next();
                thanhToanDto = new ThanhToanDto(
                        Float.valueOf(String.valueOf(obj[1])),
                        Float.valueOf(String.valueOf(obj[2])),
                        Float.valueOf(String.valueOf(obj[3])),
                        Float.valueOf(String.valueOf(obj[4])),
                        Float.valueOf(String.valueOf(obj[5])),
                        Float.valueOf(String.valueOf(obj[6]))
                );
            }
            return thanhToanDto;
        } catch (Exception e) {
            log.info(e.getMessage());
            throw e;
        }
    }
//    @Async
//    public CompletableFuture<List<HoaDon>> like(BaseRequestDto formData) {
//        SearchSpecific searchSpecific = new SearchSpecific<HoaDon>(formData.getQuery());
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
//        SearchSpecific searchSpecific = new SearchSpecific<HoaDon>(formData.getQuery());
//        return CompletableFuture.completedFuture(
//                (int) repository.count(searchSpecific)
//        );
//    }
}
