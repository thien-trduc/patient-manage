package thien.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import thien.dto.BaseRequestDto;
import thien.dto.BaseResponseDto;
import thien.dto.benhnhan.CreateBenhNhanDto;
import thien.dto.benhnhan.UpdateBenhNhanDto;
import thien.entities.BenhNhan;
import thien.service.BenhNhanService;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("benh-nhan")
@Log4j2
public class BenhNhanController {
    @Autowired
    BenhNhanService service;

    @PostMapping("list")
    @ResponseStatus(value = HttpStatus.OK)
    private BaseResponseDto<BenhNhan> find(@RequestBody BaseRequestDto formData) throws ExecutionException, InterruptedException {
        return service.find(formData);
    }

    @GetMapping("{id}")
    private BenhNhan findById(@PathVariable("id") String id) {
        return service.findById(id);
    }

    @PostMapping("search")
    @ResponseStatus(value = HttpStatus.OK)
    private BaseResponseDto<BenhNhan> search(@RequestBody BaseRequestDto formData) throws ExecutionException, InterruptedException {
        return service.search(formData);
    }

    @PostMapping
    private BenhNhan create(@RequestBody CreateBenhNhanDto formData) {
        return service.create(formData);
    }

    @PutMapping("{id}")
    private BenhNhan update(@PathVariable("id") String id, @RequestBody UpdateBenhNhanDto formData) {
        log.info(formData);
        return service.update(id, formData);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    private void delete(@PathVariable("id") String id) {
        service.delete(id);
    }
}
