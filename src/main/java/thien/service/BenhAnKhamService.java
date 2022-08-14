package thien.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import thien.dto.BaseRequestDto;
import thien.dto.BaseResponseDto;
import thien.dto.benhan.BenhAnKhamDto;
import thien.dto.benhan.CreateBenhAnKhamDto;
import thien.dto.benhan.UpdateBenhAnKhamDto;
import thien.entities.BenhAnKham;
import thien.repository.BenhAnKhamRepository;
import thien.util.Constant;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
@Log4j2
public class BenhAnKhamService {
    @Autowired
    BenhAnKhamRepository repository;

    @Autowired
    UtilService utilService;

    public BaseResponseDto<BenhAnKhamDto> find(BaseRequestDto formData) throws ExecutionException, InterruptedException {
        try {
            String maBA = utilService.valueByKeyJson(formData.getQuery(), "maBA")
                    .getAsString();
            Pageable pageable = utilService.paginate(formData);
            CompletableFuture<List<BenhAnKhamDto>> list = list(maBA, pageable);
            CompletableFuture<Integer> count = count(maBA);
            CompletableFuture.allOf(list, count).join();
            return new BaseResponseDto<>(formData.getPageIndex(), formData.getPageSize(), count.get(), list.get());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    public BenhAnKhamDto findById(Integer id) {
        try {
            BenhAnKham kham = repository.findById(id).get();
            BenhAnKham existToathuoc = repository.existToaThuoc(id);
            boolean existToaThuoc = existToathuoc != null;
            return new BenhAnKhamDto(kham, existToaThuoc);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    public BaseResponseDto<BenhAnKhamDto> searchByDate(BaseRequestDto formData) throws ExecutionException, InterruptedException, ParseException {
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

            CompletableFuture<List<BenhAnKhamDto>> list = findByBenhAnAndNgayKhamBetween(maBA, tuNgay, denNgay, pageable);
            CompletableFuture<Integer> count = countByBenhAnAndNgayKhamBetween(maBA, tuNgay, denNgay);
            CompletableFuture.allOf(list, count).join();
            return new BaseResponseDto<>(formData.getPageIndex(), formData.getPageSize(), count.get(), list.get());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    public BenhAnKham create(CreateBenhAnKhamDto formData) {
        try {
            return repository.save(formData.toEntity());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    public BenhAnKham update(Integer id, UpdateBenhAnKhamDto updateBenhAnKhamDto) {
        try {
            BenhAnKham oldEntity = repository.findById(id).get();
            BenhAnKham newEntity = updateBenhAnKhamDto.toUpdateEntity(oldEntity);
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

    @Async
    public CompletableFuture<List<BenhAnKhamDto>> list(String maBA, Pageable pageable) {
        return CompletableFuture.completedFuture(
                repository.findByBenhAn_MaBA(
                        maBA,
                        pageable
                ).parallelStream().map(kham -> {
                    BenhAnKham exist = repository.existToaThuoc(kham.getId());
                    boolean existToaThuoc = exist != null;
                    return new BenhAnKhamDto(kham, existToaThuoc);
                }).collect(Collectors.toList())
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
    public CompletableFuture<List<BenhAnKhamDto>> findByBenhAnAndNgayKhamBetween(String maBA, Date tuNgay, Date denNgay, Pageable pageable) throws ParseException {
        return CompletableFuture.completedFuture(
                repository.findByBenhAn_MaBA_AndNgayKhamBetween(
                        maBA,
                        tuNgay,
                        denNgay,
                        pageable
                ).parallelStream().map(kham -> {
                    BenhAnKham exist = repository.existToaThuoc(kham.getId());
                    boolean existToaThuoc = exist != null;
                    return new BenhAnKhamDto(kham, existToaThuoc);
                }).collect(Collectors.toList())
        );
    }

    @Async
    public CompletableFuture<Integer> countByBenhAnAndNgayKhamBetween(String maBA, Date tuNgay, Date denNgay) throws ParseException {
        return CompletableFuture.completedFuture(
                repository.countByBenhAn_MaBA_AndNgayKhamBetween(
                        maBA,
                        tuNgay,
                        denNgay
                )
        );
    }
}
