package thien.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import thien.dto.BaseRequestDto;
import thien.dto.BaseResponseDto;
import thien.dto.toathuoc.CreateToaThuocChiTietDto;
import thien.dto.toathuoc.CreateToaThuocDto;
import thien.dto.toathuoc.UpdateToaThuocChiTietDto;
import thien.dto.toathuoc.UpdateToaThuocDto;
import thien.entities.BenhAnKham;
import thien.entities.ToaThuoc;
import thien.entities.ToaThuocChiTiet;
import thien.service.ToaThuocService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("toa-thuoc")
public class ToaThuocController {
    @Autowired
    ToaThuocService service;

    @PostMapping
    public ToaThuoc create(@RequestBody CreateToaThuocDto formData) throws ExecutionException, InterruptedException {
        return service.create(formData);
    }

    @PutMapping("{id}")
    public ToaThuoc create(@PathVariable("id") String id ,@RequestBody UpdateToaThuocDto formData) throws ExecutionException, InterruptedException {
        return service.update(id,formData);
    }

    @PostMapping("list")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponseDto<ToaThuoc> find(@RequestBody BaseRequestDto formData) throws ExecutionException, InterruptedException {
        return service.find(formData);
    }

    @PostMapping("search")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponseDto<ToaThuoc> search(@RequestBody BaseRequestDto formData) throws ExecutionException, InterruptedException {
        return service.search(formData);
    }

    @GetMapping("kham-exist")
    public ToaThuoc findByKham_Id(@RequestHeader("makham") Integer maKham) {
        return service.findByKham_Id(maKham);
    }

    @GetMapping("{id}")
    public ToaThuoc findById(@PathVariable("id") String maToa) {
        return service.findById(maToa);
    }

    @GetMapping("{id}/export")
    public void exportById(@PathVariable("id") String maToa, HttpServletResponse response, HttpServletRequest request) throws IOException {
        service.exportExcelToaThuoc(maToa, response, request);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") String maToa) {
        service.delete(maToa);
    }

    @PostMapping("{id}/chi-tiet-toa")
    public ToaThuocChiTiet createChiTiet(@PathVariable("id") String maToa, @RequestBody CreateToaThuocChiTietDto formData) {
        return service.createChiTiet(maToa, formData);
    }

    @PostMapping("{id}/chi-tiet-toa/list")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponseDto<ToaThuocChiTiet> findChiTiets(@PathVariable("id") String maToa, @RequestBody BaseRequestDto formData) throws ExecutionException, InterruptedException {
        return service.findChiTiets(maToa, formData);
    }

    @PatchMapping("{id}/chi-tiet-toa")
    public ToaThuocChiTiet updateChiTiet(
            @PathVariable("id") String maToa,
            @RequestHeader("mathuoc") String maThuoc,
            @RequestBody UpdateToaThuocChiTietDto formData) {
        return service.updateChiTiet(maToa, maThuoc, formData);
    }

    @DeleteMapping("{id}/chi-tiet-toa")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") String maToa, @RequestHeader("mathuoc") String maThuoc) {
        service.deleteChiTiet(maToa,maThuoc);
    }

}
