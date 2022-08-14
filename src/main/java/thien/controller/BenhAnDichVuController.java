package thien.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import thien.dto.BaseRequestDto;
import thien.dto.BaseResponseDto;
import thien.dto.benhan.BenhAnDichVuDto;
import thien.dto.benhan.CreateBenhAnDichVuDto;
import thien.dto.benhan.UpdateBenhAnDichVuDto;
import thien.entities.BenhAnDichVu;
import thien.service.BenhAnDichVuService;

import java.text.ParseException;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("dich-vu-su-dung")
public class BenhAnDichVuController {
    BenhAnDichVuService service;

    @Autowired
    public BenhAnDichVuController(BenhAnDichVuService service) {
        this.service = service;
    }

    @PostMapping
    private BenhAnDichVu create(@RequestBody CreateBenhAnDichVuDto formData) {
        return service.create(formData);
    }

    @PatchMapping("{id}")
    private BenhAnDichVu update(@PathVariable("id") String id,@RequestBody UpdateBenhAnDichVuDto formData) {
        return service.update(id,formData);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private void delete(@PathVariable("id") String id) {
        service.delete(id);
    }

    @PostMapping("search")
    @ResponseStatus(value = HttpStatus.OK)
    public BaseResponseDto<BenhAnDichVu> searchByDate(@RequestBody BaseRequestDto formData) throws ExecutionException, InterruptedException, ParseException {
        return service.searchByDate(formData);
    }

    @PostMapping("list")
    @ResponseStatus(value = HttpStatus.OK)
    public BaseResponseDto<BenhAnDichVu> find(@RequestBody BaseRequestDto formData) throws ExecutionException, InterruptedException {
        return service.find(formData);
    }

    @GetMapping("{id}")
    public BenhAnDichVuDto findById(@PathVariable("id") String id) {
        return service.findById(id);
    }

    @DeleteMapping("hinh-anh")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void  deleteHinhAnhChiTiet(@RequestParam("idHinhAnh") String idHinhAnh) {
        service.deleteHinhAnhChiTiet(idHinhAnh);
    }
}
