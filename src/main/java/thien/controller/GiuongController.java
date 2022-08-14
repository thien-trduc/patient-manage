package thien.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import thien.dto.BaseRequestDto;
import thien.dto.BaseResponseDto;
import thien.dto.giuong.CreateGiuongDto;
import thien.dto.giuong.UpdateGiuongDto;
import thien.entities.Giuong;
import thien.service.GiuongService;
import thien.service.GiuongService;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("giuong")
public class GiuongController {

    GiuongService service;

    @Autowired
    public GiuongController(GiuongService service) {
        this.service = service;
    }

    @PostMapping("list")
    private BaseResponseDto<Giuong> find(@RequestBody BaseRequestDto formData) throws ExecutionException, InterruptedException {
        return service.find(formData);
    }

    @GetMapping("{id}")
    private Giuong findById(@PathVariable("id") Integer id) {
        return service.findById(id);
    }

    @PostMapping("search")
    private BaseResponseDto<Giuong> search(@RequestBody BaseRequestDto formData) throws ExecutionException, InterruptedException {
        return service.search(formData);
    }

    @PostMapping
    private Giuong create(@RequestBody CreateGiuongDto formData) {
        return service.create(formData);
    }

    @PutMapping("{id}")
    private Giuong update(@PathVariable("id") Integer id, @RequestBody UpdateGiuongDto formData) {
        return service.update(id, formData);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    private void delete(@PathVariable("id") Integer id) {
        service.delete(id);
    }
}
