package thien.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import thien.dto.BaseRequestDto;
import thien.dto.BaseResponseDto;
import thien.dto.phong.*;
import thien.entities.Phong;
import thien.entities.PhongCapNhatGia;
import thien.entities.PhongGiuong;
import thien.service.PhongGiuongService;
import thien.service.PhongService;

import java.util.Date;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("phong")
public class PhongController {
    PhongService phongService;
    PhongGiuongService phongGiuongService;

    @Autowired
    public PhongController(PhongService phongService, PhongGiuongService phongGiuongService) {
        this.phongService = phongService;
        this.phongGiuongService = phongGiuongService;
    }

    @PostMapping
    public Phong create(@RequestBody CreatePhongDto formData) {
        return phongService.create(formData);
    }

    @PostMapping("list")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponseDto<PhongDto> find(@RequestBody BaseRequestDto formData) throws ExecutionException, InterruptedException {
        return phongService.find(formData);
    }

    @PostMapping("search")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponseDto<PhongDto> search(@RequestBody BaseRequestDto formData) throws ExecutionException, InterruptedException {
        return phongService.search(formData);
    }

    @PutMapping("{id}")
    public Phong create(@PathVariable("id") Integer id, @RequestBody UpdatePhongDto formData) {
        return phongService.update(id, formData);
    }

    @GetMapping("{id}")
    public PhongDto findById(@PathVariable("id") Integer id) {
        return phongService.findById(id);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Integer id) {
        phongService.delete(id);
    }

    @PostMapping("{id}/gia")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponseDto<PhongCapNhatGia> findGias(
            @PathVariable("id") Integer id,
            @RequestBody BaseRequestDto formData
    ) throws ExecutionException, InterruptedException {
        return phongService.findGias(id, formData);
    }

    @PostMapping("{id}/gia/them")
    public PhongCapNhatGia createGia(
            @PathVariable("id") Integer id,
            @RequestBody CreateGiaPhongDto formData
    ) {
        return phongService.createGia(id, formData);
    }

    @DeleteMapping("{id}/gia")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGia(
            @PathVariable("id") Integer id,
            @RequestHeader("ngay") Date ngay
    ) {
        phongService.deleteGia(id, ngay);
    }

    @PostMapping("{id}/giuong/them")
    public PhongGiuong createPhongGiuong(@PathVariable("id") Integer id,@RequestBody CreatePhongGiuongDto formData) {
        return phongGiuongService.create(formData);
    }

    @PostMapping("{id}/giuong/list")
    public BaseResponseDto<PhongGiuongDto> getPhongGiuongs(@PathVariable("id") Integer id, @RequestBody BaseRequestDto formData) throws ExecutionException, InterruptedException {
        return phongGiuongService.findByPhong_MaPhong(formData);
    }

    @DeleteMapping("{id}/giuong/{idChiTiet}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removePhongGiuong(@PathVariable("id") Integer id,@PathVariable("idChiTiet") Integer idChiTiet){
        phongGiuongService.delete(idChiTiet);
    }

    @GetMapping("{id}/giuong/{idChiTiet}")
    public PhongGiuongDto getPhongGiuong(@PathVariable("id") Integer id,@PathVariable("idChiTiet") Integer idChiTiet){
        return phongGiuongService.findById(idChiTiet);
    }
}
