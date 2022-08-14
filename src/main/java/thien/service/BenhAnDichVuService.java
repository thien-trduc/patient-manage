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
import thien.dto.benhan.BenhAnDichVuDto;
import thien.dto.benhan.CreateBenhAnDichVuDto;
import thien.dto.benhan.CreateBenhAnDichVuHInhAnhDto;
import thien.dto.benhan.UpdateBenhAnDichVuDto;
import thien.entities.BenhAnDichVu;
import thien.entities.BenhAnDichVuHinhAnh;
import thien.repository.BenhAnDichVuHinhAnhRepository;
import thien.repository.BenhAnDichVuRepository;
import thien.util.Constant;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@Log4j2
public class BenhAnDichVuService {
    BenhAnDichVuRepository repository;
    BenhAnDichVuHinhAnhRepository benhAnDichVuHinhAnhRepository;
    UtilService utilService;

    @Autowired
    public BenhAnDichVuService(BenhAnDichVuRepository repository, BenhAnDichVuHinhAnhRepository benhAnDichVuHinhAnhRepository, UtilService utilService) {
        this.repository = repository;
        this.benhAnDichVuHinhAnhRepository = benhAnDichVuHinhAnhRepository;
        this.utilService = utilService;
    }

    public BaseResponseDto<BenhAnDichVu> find(BaseRequestDto formData) throws ExecutionException, InterruptedException {
        try {
            String maBA = utilService.valueByKeyJson(formData.getQuery(), "maBA")
                    .getAsString();
            Pageable pageable = utilService.paginate(formData);
            CompletableFuture<List<BenhAnDichVu>> list = list(maBA, pageable);
            CompletableFuture<Integer> count = count(maBA);
            CompletableFuture.allOf(list, count).join();
            return new BaseResponseDto<>(formData.getPageIndex(), formData.getPageSize(), count.get(), list.get());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    public BenhAnDichVuDto findById(String id) {
        try {
            BenhAnDichVu entity = repository.findById(id).get();
            List<BenhAnDichVuHinhAnh> benhAnDichVuHinhAnhs = benhAnDichVuHinhAnhRepository.findByBenhAnDichVu_Id(id);
            return new BenhAnDichVuDto(entity, benhAnDichVuHinhAnhs);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    public BaseResponseDto<BenhAnDichVu> searchByDate(BaseRequestDto formData) throws ExecutionException, InterruptedException, ParseException {
        try {
            if (formData.getQuery() == null || formData.getQuery().isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
            String strTuNgay = utilService.valueByKeyJson(formData.getQuery(), "tuNgay")
                    .getAsString();
            String strDenNgay = utilService.valueByKeyJson(formData.getQuery(), "denNgay")
                    .getAsString();
            String maBA = utilService.valueByKeyJson(formData.getQuery(), "maBA")
                    .getAsString();
            Date tuNgay = (new SimpleDateFormat(Constant.DATE_FORMAT)).parse(strTuNgay);
            Date denNgay = (new SimpleDateFormat(Constant.DATE_FORMAT)).parse(strDenNgay);
            Pageable pageable = utilService.paginate(formData);

            CompletableFuture<List<BenhAnDichVu>> list = findByBenhAnAndNgayBetween(maBA, tuNgay, denNgay, pageable);
            CompletableFuture<Integer> count = countByBenhAnAndNgayKhamBetween(maBA, tuNgay, denNgay);
            CompletableFuture.allOf(list, count).join();
            return new BaseResponseDto<>(formData.getPageIndex(), formData.getPageSize(), count.get(), list.get());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @Transactional(rollbackFor = {Exception.class, Throwable.class}, isolation = Isolation.SERIALIZABLE,  propagation = Propagation.REQUIRED)
    public BenhAnDichVu create(CreateBenhAnDichVuDto formData) {
        try {
            BenhAnDichVu benhAnDichVu = repository.save(formData.toEntity());
            if(formData.getHinhAnhs().size() > 0) {
                log.warn(benhAnDichVu.getId());
                formData.getHinhAnhs().stream().forEach(hinhAnh -> {
                    CreateBenhAnDichVuHInhAnhDto dtoHinhAnh = new CreateBenhAnDichVuHInhAnhDto(hinhAnh,benhAnDichVu.getId());
                    benhAnDichVuHinhAnhRepository.save(dtoHinhAnh.toEntity());
                });
            }
            return benhAnDichVu;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @Transactional(rollbackFor = {Exception.class, Throwable.class}, isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
    public BenhAnDichVu update(String id, UpdateBenhAnDichVuDto formData) {
        try {
            BenhAnDichVu oldEntity = repository.findById(id).get();
            BenhAnDichVu newEntity = repository.save(formData.toUpdateEntity(oldEntity));
            formData.getHinhAnhs().stream().forEach(hinhAnh -> {
                CreateBenhAnDichVuHInhAnhDto dtoHinhAnh = new CreateBenhAnDichVuHInhAnhDto(hinhAnh,newEntity.getId());
                benhAnDichVuHinhAnhRepository.save(dtoHinhAnh.toEntity());
            });
            return newEntity;
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

    public void deleteHinhAnhChiTiet(String id) {
        try {
            benhAnDichVuHinhAnhRepository.deleteById(id);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @Async
    public CompletableFuture<List<BenhAnDichVu>> list(String maBA, Pageable pageable) {
        return CompletableFuture.completedFuture(
                repository.findByBenhAn_MaBA(
                        maBA,
                        pageable
                )
        );
    }

    @Async
    public CompletableFuture<Integer> count(String maBA) {
        return CompletableFuture.completedFuture(
                repository.countByBenhAn_MaBA(
                        maBA
                )
        );
    }

    @Async
    public CompletableFuture<List<BenhAnDichVu>> findByBenhAnAndNgayBetween(String maBA, Date tuNgay, Date denNgay, Pageable pageable) throws ParseException {
        return CompletableFuture.completedFuture(
                repository.findByBenhAn_MaBA_AndNgayBetween(
                        maBA,
                        tuNgay,
                        denNgay,
                        pageable
                )
        );
    }

    @Async
    public CompletableFuture<Integer> countByBenhAnAndNgayKhamBetween(String maBA, Date tuNgay, Date denNgay) throws ParseException {
        return CompletableFuture.completedFuture(
                repository.countByBenhAn_MaBA_AndNgayBetween(
                        maBA,
                        tuNgay,
                        denNgay
                )
        );
    }
}
