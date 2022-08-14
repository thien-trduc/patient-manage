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
import thien.dto.benhan.*;
import thien.entities.*;

import thien.repository.BenhAnXepGiuongRepository;
import thien.repository.PhongGiuongRepository;
import thien.util.Constant;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@Log4j2
public class BenhAnXepGiuongService {
    BenhAnXepGiuongRepository repository;
    PhongGiuongRepository phongGiuongRepository;
    UtilService utilService;

    @Autowired
    public BenhAnXepGiuongService(BenhAnXepGiuongRepository repository, PhongGiuongRepository phongGiuongRepository, UtilService utilService) {
        this.repository = repository;
        this.phongGiuongRepository = phongGiuongRepository;
        this.utilService = utilService;
    }

    public BaseResponseDto<BenhAnXepGiuong> find(BaseRequestDto formData) throws ExecutionException, InterruptedException {
        try {
            String maBA = utilService.valueByKeyJson(formData.getQuery(), "maBA")
                    .getAsString();
            Pageable pageable = utilService.paginate(formData);
            CompletableFuture<List<BenhAnXepGiuong>> list = list(maBA, pageable);
            CompletableFuture<Integer> count = count(maBA);
            CompletableFuture.allOf(list, count).join();
            return new BaseResponseDto<>(formData.getPageIndex(), formData.getPageSize(), count.get(), list.get());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    public BenhAnXepGiuong findById(String maBA, Integer ctPhongGiuong) {
        try {
            BenhAnXepGiuongID id = new BenhAnXepGiuongID(ctPhongGiuong, maBA);
            return repository.findById(id).get();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @Transactional(rollbackFor = {Exception.class, Throwable.class}, isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
    public void traGiuongXuatVien(String maBA) {
        try {
            List<BenhAnXepGiuong> benhAnXepGiuongs = repository.findByBenhAn_MaBA(maBA);
            if(benhAnXepGiuongs != null && benhAnXepGiuongs.size() > 0 ) {
                for(BenhAnXepGiuong benhAnXepGiuong : benhAnXepGiuongs) {
                    PhongGiuong phongGiuong = phongGiuongRepository.findById(benhAnXepGiuong.getCtPhongGiuong().getId()).get();
                    phongGiuong.setTrangThai(false);
                    phongGiuongRepository.save(phongGiuong);
                }
            }
        }catch (Exception e) {
            throw e;
        }
    }

    public BaseResponseDto<BenhAnXepGiuong> searchByDate(BaseRequestDto formData) throws ExecutionException, InterruptedException, ParseException {
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

            CompletableFuture<List<BenhAnXepGiuong>> list = findByBenhAnAndNgayBetween(maBA, tuNgay, denNgay, pageable);
            CompletableFuture<Integer> count = countByBenhAnAndNgayKhamBetween(maBA, tuNgay, denNgay);
            CompletableFuture.allOf(list, count).join();
            return new BaseResponseDto<>(formData.getPageIndex(), formData.getPageSize(), count.get(), list.get());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @Transactional(rollbackFor = {Exception.class, Throwable.class}, isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
    public BenhAnXepGiuong create(CreateBenhAnXepGiuongDto formData) {
        try {
            BenhAnXepGiuong entity = repository.save(formData.toEntity());

            Integer idPhongGiuong = entity.getCtPhongGiuong().getId();
            PhongGiuong phongGiuong = phongGiuongRepository.findById(idPhongGiuong).get();
            phongGiuong.setTrangThai(true);
            phongGiuongRepository.save(phongGiuong);

            return entity;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @Transactional(rollbackFor = {Exception.class, Throwable.class}, isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
    public BenhAnXepGiuong update(String maBA, Integer idCtGiuong, UpdateBenhAnXepGiuongDto formData) {
        try {
            BenhAnXepGiuongID id = new BenhAnXepGiuongID(idCtGiuong, maBA);
            BenhAnXepGiuong oldEntity = repository.findById(id).get();
            BenhAnXepGiuong newEntity = repository.save(formData.toUpdateEntity(oldEntity));

            Integer oldPhongGiuongId = oldEntity.getCtPhongGiuong().getId();
            Integer newPhongGiuongId = newEntity.getCtPhongGiuong().getId();

            if(!Objects.equals(newPhongGiuongId, oldPhongGiuongId)) {
                PhongGiuong oldPhongGiuong = phongGiuongRepository.findById(oldPhongGiuongId).get();
                oldPhongGiuong.setTrangThai(false);
                phongGiuongRepository.save(oldPhongGiuong);

                PhongGiuong newPhongGiuong = phongGiuongRepository.findById(newPhongGiuongId).get();
                newPhongGiuong.setTrangThai(true);
                phongGiuongRepository.save(newPhongGiuong);
            }
            return newEntity;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @Transactional(rollbackFor = {Exception.class, Throwable.class}, isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
    public void delete(String maBA, Integer idCtGiuong) {
        try {
            BenhAnXepGiuongID id = new BenhAnXepGiuongID(idCtGiuong, maBA);
            BenhAnXepGiuong oldEntity = repository.findById(id).get();

            Integer oldPhongGiuongId = oldEntity.getCtPhongGiuong().getId();
            PhongGiuong oldPhongGiuong = phongGiuongRepository.findById(oldPhongGiuongId).get();
            oldPhongGiuong.setTrangThai(false);
            phongGiuongRepository.save(oldPhongGiuong);

            repository.deleteById(id);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }


    @Async
    public CompletableFuture<List<BenhAnXepGiuong>> list(String maBA, Pageable pageable) {
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
    public CompletableFuture<List<BenhAnXepGiuong>> findByBenhAnAndNgayBetween(String maBA, Date tuNgay, Date denNgay, Pageable pageable) throws ParseException {
        return CompletableFuture.completedFuture(
                repository.findByBenhAn_MaBA_AndNgayThueBetween(
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
                repository.countByBenhAn_MaBA_AndNgayThueBetween(
                        maBA,
                        tuNgay,
                        denNgay
                )
        );
    }
}
