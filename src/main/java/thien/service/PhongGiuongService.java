package thien.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import thien.dto.BaseRequestDto;
import thien.dto.BaseResponseDto;
import thien.dto.dichvu.DichVuDto;
import thien.dto.phong.CreatePhongGiuongDto;
import thien.dto.phong.PhongGiuongDto;
import thien.entities.DichVu;
import thien.entities.PhongGiuong;
import thien.repository.PhongGiuongRepository;
import thien.repository.PhongRepository;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
@Log4j2
public class PhongGiuongService {
    PhongGiuongRepository repository;
    PhongRepository phongRepository;
    UtilService utilService;

    @Autowired
    public PhongGiuongService(PhongGiuongRepository repository,UtilService utilService, PhongRepository phongRepository) {
        this.utilService = utilService;
        this.repository = repository;
        this.phongRepository = phongRepository;
    }

    public PhongGiuong create(CreatePhongGiuongDto formData) {
        try {
            return repository.save(formData.toEntity());
        } catch (Exception e) {
            log.warn(e.getMessage());
            throw e;
        }
    }

    public PhongGiuongDto findById(Integer id) {
        try {
            PhongGiuong phongGiuong = repository.findById(id).get();
            return getPhongGiuongAndGia(phongGiuong);
        } catch (Exception e) {
            log.warn(e.getMessage());
            throw e;
        }
    }

    public void delete(Integer id) {
        try {
            repository.deleteById(id);
        } catch (Exception e) {
            log.warn(e.getMessage());
            throw e;
        }
    }

    public BaseResponseDto<PhongGiuongDto> findByPhong_MaPhong(BaseRequestDto formData) throws ExecutionException, InterruptedException {
       try {
           Integer maPhong = utilService.valueByKeyJson(formData.getQuery(), "maPhong")
                   .getAsInt();
           Pageable pageable = utilService.paginate(formData);
           CompletableFuture<List<PhongGiuongDto>> list = CompletableFuture.supplyAsync(()-> {
               List<PhongGiuong> phongGiuongs = repository.findByPhong_MaPhong(maPhong , pageable);
               return phongGiuongs.stream().map(this::getPhongGiuongAndGia).collect(Collectors.toList());
           });
           CompletableFuture<Integer> count = CompletableFuture.supplyAsync(()-> repository.countByPhong_MaPhong(maPhong));
           CompletableFuture.allOf(list,count).join();
           return new BaseResponseDto<>(formData.getPageIndex(),formData.getPageSize(),count.get(),list.get());
       } catch (Exception e) {
           throw e;
       }
    }

    public BaseResponseDto<PhongGiuongDto> findByGiuong_SoGiuongContaining(BaseRequestDto formData) throws ExecutionException, InterruptedException {
        try {
            String soGiuong = utilService.valueByKeyJson(formData.getQuery(), "soGiuong")
                    .getAsString();
            Pageable pageable = utilService.paginate(formData);
            CompletableFuture<List<PhongGiuongDto>> list = CompletableFuture.supplyAsync(()-> {
                List<PhongGiuong> phongGiuongs = repository.findByGiuong_SoGiuongContaining(soGiuong , pageable);
                return phongGiuongs.stream().map(this::getPhongGiuongAndGia).collect(Collectors.toList());
            });
            CompletableFuture<Integer> count = CompletableFuture.supplyAsync(()-> repository.countByGiuong_SoGiuongContaining(soGiuong));
            CompletableFuture.allOf(list,count).join();
            return new BaseResponseDto<>(formData.getPageIndex(),formData.getPageSize(),count.get(),list.get());
        } catch (Exception e) {
            throw e;
        }
    }

    public PhongGiuongDto getPhongGiuongAndGia(PhongGiuong entity) {
        Integer maPhong = entity.getPhong().getMaPhong();
        Date current = new Date();
        Float gia = phongRepository.getGiaPhong(current, maPhong);
        return new PhongGiuongDto(entity, gia);
    }
}
