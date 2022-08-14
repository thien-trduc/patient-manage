package thien.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import thien.dto.BaseRequestDto;
import thien.dto.BaseResponseDto;
import thien.dto.nhanvien.CreateNhanVienDto;
import thien.dto.nhanvien.UpdateNhanVienDto;
import thien.entities.LoaiNhanVien;
import thien.entities.NhanVien;
import thien.service.LoaiNhanVienService;
import thien.service.NhanVienService;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Log4j2
@RestController
@RequestMapping("nhan-vien")
public class NhanVienController {
    @Autowired
    NhanVienService service;

    @Autowired
    LoaiNhanVienService loaiNhanVienService;

    @PostMapping("list")
    private BaseResponseDto<NhanVien> find(@RequestBody BaseRequestDto formData) throws ExecutionException, InterruptedException {
        return service.find(formData);
    }

    @PostMapping("find-by-loai")
    private BaseResponseDto<NhanVien> findByLoaiNV(@RequestBody BaseRequestDto formData) throws ExecutionException, InterruptedException {
        return service.findByLoaiNV(formData);
    }

    @GetMapping("loai")
    private List<LoaiNhanVien> findLoaiNhanVien() throws ExecutionException, InterruptedException {
        return loaiNhanVienService.find(
                new BaseRequestDto(1, 10000, null, null)
        ).getRows();
    }

    @GetMapping("{id}")
    private NhanVien findById(@PathVariable("id") String id) {
        return service.findById(id);
    }

    @PostMapping("search")
    private BaseResponseDto<NhanVien> search(@RequestBody BaseRequestDto formData) throws ExecutionException, InterruptedException {
        return service.search(formData);
    }

    @PostMapping("search-nhan-vien-benh-an")
    private BaseResponseDto<NhanVien> searchByHoTenAndLoai(@RequestBody BaseRequestDto formData) throws ExecutionException, InterruptedException {
        return service.searchByHotenAndLoaiNv(formData);
    }

    @PostMapping
    private NhanVien create(@RequestBody CreateNhanVienDto formData) {
        return service.create(formData);
    }

    @PutMapping("{id}")
    private NhanVien update(@PathVariable("id") String id, @RequestBody UpdateNhanVienDto formData) {
        return service.update(id, formData);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    private void delete(@PathVariable("id") String id) {
        service.delete(id);
    }

}
