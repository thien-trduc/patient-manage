package thien.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import thien.dto.BaseRequestDto;
import thien.dto.BaseResponseDto;
import thien.dto.thuoc.CreateGiaThuocDto;
import thien.dto.thuoc.CreateThuocDto;
import thien.dto.thuoc.ThuocDto;
import thien.dto.thuoc.UpdateThuocDto;
import thien.entities.Thuoc;
import thien.entities.ThuocCapNhatGia;
import thien.service.ThuocService;

import java.util.Date;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("thuoc")
public class ThuocController {
    @Autowired
    ThuocService service;

    @PostMapping("list")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponseDto<ThuocDto> find(@RequestBody BaseRequestDto formData) throws ExecutionException, InterruptedException {
        return service.find(formData);
    }

    @PostMapping("search")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponseDto<ThuocDto> search(@RequestBody BaseRequestDto formData) throws ExecutionException, InterruptedException {
        return service.search(formData);
    }

    @PostMapping
    public Thuoc create(@RequestBody CreateThuocDto formData) {
        return service.create(formData);
    }

    @PutMapping("{id}")
    public Thuoc create(@PathVariable("id") String id ,@RequestBody UpdateThuocDto formData) {
        return service.update(id, formData);
    }

    @GetMapping("{id}")
    public ThuocDto findById(@PathVariable("id") String id) {
        return service.findById(id);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") String id) {
        service.delete(id);
    }

    @PostMapping("{id}/gia")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponseDto<ThuocCapNhatGia> findGias(
            @PathVariable("id") String maThuoc,
            @RequestBody BaseRequestDto formData
    ) throws ExecutionException, InterruptedException {
        return service.findGias(maThuoc, formData);
    }

    @PostMapping("{id}/gia/them")
    public ThuocCapNhatGia createGia(
            @PathVariable("id") String maThuoc,
            @RequestBody CreateGiaThuocDto formData
    ) {
        return service.createGia(maThuoc, formData);
    }

    @DeleteMapping("{id}/gia")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGia(
            @PathVariable("id") String maThuoc,
            @RequestHeader("ngay") Date ngay
    ) {
        service.deleteGia(maThuoc, ngay);
    }
}
