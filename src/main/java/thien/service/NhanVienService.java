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
import thien.dto.nhanvien.CreateNhanVienDto;
import thien.dto.nhanvien.UpdateNhanVienDto;
import thien.dto.nhanvienuser.CreateNhanVienUserDto;
import thien.entities.Khoa;
import thien.entities.LoaiNhanVien;
import thien.entities.NhanVien;
import thien.entities.NhanVienUser;
import thien.repository.LoaiNhanVienRepository;
import thien.repository.NhanVienRepository;
import thien.repository.NhanVienUserRepository;
import thien.util.core.QuerySpecific;
import thien.util.core.SearchSpecific;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@Log4j2
public class NhanVienService {
    NhanVienRepository repository;
    NhanVienUserRepository userRepository;
    UtilService utilService;

    @Autowired
    public NhanVienService(NhanVienRepository repository, NhanVienUserRepository userRepository, UtilService utilService) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.utilService = utilService;
    }

    public BaseResponseDto<NhanVien> find(BaseRequestDto formData) throws ExecutionException, InterruptedException {
        try {
            CompletableFuture<List<NhanVien>> list = list(formData);
            CompletableFuture<Integer> count = count();
            CompletableFuture.allOf(list, count).join();
            return new BaseResponseDto<>(formData.getPageIndex(), formData.getPageSize(), count.get(), list.get());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    public NhanVien findById(String id) {
        try {
            return repository.findById(id).get();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    public BaseResponseDto<NhanVien> search(BaseRequestDto formData) throws ExecutionException, InterruptedException {
        try {
            CompletableFuture<List<NhanVien>> list = this.like(formData);
            CompletableFuture<Integer> count = this.countLike(formData);
            CompletableFuture.allOf(list, count).join();
            return new BaseResponseDto<>(formData.getPageIndex(), formData.getPageSize(), count.get(), list.get());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    public BaseResponseDto<NhanVien> findByLoaiNV(BaseRequestDto formData) throws ExecutionException, InterruptedException {
        try {
            Integer maLoai = utilService.valueByKeyJson(formData.getQuery(), "loaiNV")
                    .getAsJsonObject()
                    .get("maLoaiNV")
                    .getAsInt();
            Pageable pageable = utilService.paginate(formData);
            LoaiNhanVien loaiNV = new LoaiNhanVien();
            loaiNV.setMaLoaiNV(maLoai);

            CompletableFuture<List<NhanVien>> list = findByLoai(loaiNV,pageable);
            CompletableFuture<Integer> count = countByLoai(loaiNV);
            CompletableFuture.allOf(list, count).join();
            return new BaseResponseDto<NhanVien>(formData.getPageIndex(), formData.getPageSize(), count.get(), list.get());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    public BaseResponseDto<NhanVien> searchByHotenAndLoaiNv(BaseRequestDto formData) throws ExecutionException, InterruptedException {
        try {
            Integer maLoai = utilService.valueByKeyJson(formData.getQuery(), "loaiNV")
                    .getAsJsonObject()
                    .get("maLoaiNV")
                    .getAsInt();
            String hoTen = utilService.valueByKeyJson(formData.getQuery(), "hoTen")
                    .getAsString()
                    .toUpperCase();
            Pageable pageable = utilService.paginate(formData);
            LoaiNhanVien loaiNV = new LoaiNhanVien();
            loaiNV.setMaLoaiNV(maLoai);
            CompletableFuture<List<NhanVien>> list = findByLoaiNVAndHoTenContaining(loaiNV,hoTen,pageable);
            CompletableFuture<Integer> count = countByLoaiNVAndHoTenContaining(loaiNV,hoTen);
            CompletableFuture.allOf(list, count).join();
            return new BaseResponseDto<>(formData.getPageIndex(), formData.getPageSize(), count.get(), list.get());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @Transactional(rollbackFor = {Exception.class, Throwable.class}, isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
    public NhanVien create(CreateNhanVienDto formData) {
        try {
            NhanVien nv = repository.save(formData.toEntity());
            CreateNhanVienUserDto userDto = new CreateNhanVienUserDto(nv.getEmail(), nv.getMaNV());
            userRepository.save(userDto.toEntity());
            return nv;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    public NhanVien update(String id, UpdateNhanVienDto updateNhanVienDto) {
        try {
            NhanVien oldEntity = repository.findById(id).get();
            NhanVien newEntity = updateNhanVienDto.toUpdateEntity(oldEntity);
            newEntity.setMaNV(id);
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
    public CompletableFuture<List<NhanVien>> list(BaseRequestDto formData) {
        QuerySpecific<NhanVien> querySpecific = new QuerySpecific<>(formData.getQuery());
        return CompletableFuture.completedFuture(
                this.repository.findAll(
                        querySpecific,
                        this.utilService.paginate(formData)
                ).getContent()
        );
    }

    @Async
    public CompletableFuture<Integer> count() {
        return CompletableFuture.completedFuture(
                (int) repository.count()
        );
    }

    @Async
    public CompletableFuture<List<NhanVien>> like(BaseRequestDto formData) {
        SearchSpecific<NhanVien> searchSpecific = new SearchSpecific<>(formData.getQuery());
        return CompletableFuture.completedFuture(
                this.repository.findAll(
                        searchSpecific,
                        this.utilService.paginate(formData)
                ).getContent()
        );
    }

    @Async
    public CompletableFuture<Integer> countLike(BaseRequestDto formData) {
        SearchSpecific<NhanVien> searchSpecific = new SearchSpecific<>(formData.getQuery());
        return CompletableFuture.completedFuture(
                (int) this.repository.count(
                        searchSpecific
                )
        );
    }

    @Async
    public CompletableFuture<List<NhanVien>> findByLoai(LoaiNhanVien loaiNV, Pageable pageable) {
        return CompletableFuture.completedFuture(
                repository.findByLoaiNV(
                        loaiNV,
                        pageable
                )
        );
    }

    @Async
    public CompletableFuture<Integer> countByLoai(LoaiNhanVien loaiNV) {
        return CompletableFuture.completedFuture(
                repository.countByLoaiNV(
                        loaiNV
                )
        );
    }

    @Async
    public CompletableFuture<List<NhanVien>> findByLoaiNVAndHoTenContaining(LoaiNhanVien loaiNV, String hoTen, Pageable pageable) {
        return CompletableFuture.completedFuture(
                repository.findByLoaiNVAndHoTenContaining(
                        loaiNV,
                        hoTen,
                        pageable
                        
                )
        );
    }

    @Async
    public CompletableFuture<Integer> countByLoaiNVAndHoTenContaining(LoaiNhanVien loaiNV, String hoTen) {
        return CompletableFuture.completedFuture(
                (int) repository.countByLoaiNVAndHoTenContaining(
                        loaiNV,
                        hoTen
                )
        );
    }
}
