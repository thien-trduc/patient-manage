package thien.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import thien.dto.BaseRequestDto;
import thien.dto.BaseResponseDto;
import thien.dto.dichvu.CreateDichVuDto;
import thien.dto.dichvu.CreateGiaDichVuDto;
import thien.dto.dichvu.DichVuDto;
import thien.dto.dichvu.UpdateDichVuDto;
import thien.entities.DichVu;
import thien.entities.DichVuCapNhatGia;
import thien.service.DichVuService;

import java.util.Date;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("dich-vu")
public class DichVuController {
    private DichVuService service;

    public DichVuController(DichVuService service) {
        this.service = service;
    }

    @PostMapping("list")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponseDto<DichVuDto> find(@RequestBody BaseRequestDto formData) throws ExecutionException, InterruptedException {
        return service.find(formData);
    }

    @PostMapping("search")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponseDto<DichVuDto> search(@RequestBody BaseRequestDto formData) throws ExecutionException, InterruptedException {
        return service.search(formData);
    }

    @PostMapping
    public DichVu create(@RequestBody CreateDichVuDto formData) {
        return service.create(formData);
    }

    @PutMapping("{id}")
    public DichVu create(@PathVariable("id") String id ,@RequestBody UpdateDichVuDto formData) {
        return service.update(id, formData);
    }

    @GetMapping("{id}")
    public DichVuDto findById(@PathVariable("id") String id) {
        return service.findById(id);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") String id) {
        service.delete(id);
    }

    @PostMapping("{id}/gia")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponseDto<DichVuCapNhatGia> findGias(
            @PathVariable("id") String maDichVu,
            @RequestBody BaseRequestDto formData
    ) throws ExecutionException, InterruptedException {
        return service.findGias(maDichVu, formData);
    }

    @PostMapping("{id}/gia/them")
    public DichVuCapNhatGia createGia(
            @PathVariable("id") String maDichVu,
            @RequestBody CreateGiaDichVuDto formData
    ) {
        return service.createGia(maDichVu, formData);
    }

    @DeleteMapping("{id}/gia")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGia(
            @PathVariable("id") String maDichVu,
            @RequestHeader("ngay") Date ngay
    ) {
        service.deleteGia(maDichVu, ngay);
    }
}
