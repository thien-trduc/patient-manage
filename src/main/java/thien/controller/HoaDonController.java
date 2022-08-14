package thien.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import thien.dto.BaseRequestDto;
import thien.dto.BaseResponseDto;
import thien.dto.hoadon.CreateHoaDonDto;
import thien.dto.hoadon.UpdateHoaDonDto;
import thien.entities.HoaDon;
import thien.service.HoaDonService;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("hoa-don")
public class HoaDonController {
    @Autowired
    HoaDonService service;

    @PostMapping("list")
    private BaseResponseDto<HoaDon> find(@RequestBody BaseRequestDto formData) throws ExecutionException, InterruptedException {
        return service.find(formData);
    }

    @GetMapping("{id}")
    private HoaDon findById(@PathVariable("id") String id) {
        return service.findById(id);
    }

//    @PostMapping("search")
//    private BaseResponseDto<HoaDon> search(@RequestBody BaseRequestDto formData) throws ExecutionException, InterruptedException {
//        return service.search(formData);
//    }

    @PostMapping
    private HoaDon create(@RequestBody CreateHoaDonDto formData) {
        return service.create(formData);
    }

    @PutMapping("{id}")
    private HoaDon update(@PathVariable("id") String id, @RequestBody UpdateHoaDonDto formData) {
        return service.update(id, formData);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    private void delete(@PathVariable("id") String id) {
        service.delete(id);
    }
}
