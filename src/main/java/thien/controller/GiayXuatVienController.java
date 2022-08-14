package thien.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import thien.dto.BaseRequestDto;
import thien.dto.BaseResponseDto;

import thien.dto.phieuxuatvien.CreateGiayXuatVienDto;
import thien.dto.phieuxuatvien.UpdateGiayXuatVienDto;
import thien.entities.GiayXuatVien;
import thien.service.GiayXuatVienService;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("xuat-vien")
public class GiayXuatVienController {

    GiayXuatVienService service;

    @Autowired
    public GiayXuatVienController(GiayXuatVienService service) {
        this.service = service;
    }

    @PostMapping("list")
    private BaseResponseDto<GiayXuatVien> find(@RequestBody BaseRequestDto formData) throws ExecutionException, InterruptedException {
        return service.find(formData);
    }



    @GetMapping("{id}")
    private GiayXuatVien findById(@PathVariable("id") String id) {
        return service.findById(id);
    }

    @PostMapping
    private GiayXuatVien create(@RequestBody CreateGiayXuatVienDto formData) {
        return service.create(formData);
    }

    @PutMapping("{id}")
    private GiayXuatVien update(@PathVariable("id") String id, @RequestBody UpdateGiayXuatVienDto formData) {
        return service.update(id, formData);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    private void delete(@PathVariable("id") String id) {
        service.delete(id);
    }

}
