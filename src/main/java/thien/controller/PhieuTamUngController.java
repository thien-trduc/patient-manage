package thien.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import thien.dto.BaseRequestDto;
import thien.dto.BaseResponseDto;
import thien.dto.phieutamung.CreatePhieuTamUngDto;
import thien.dto.phieutamung.UpdatePhieuTamUngDto;
import thien.entities.PhieuTamUng;
import thien.service.PhieuTamUngService;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("tam-ung")
public class PhieuTamUngController {
    PhieuTamUngService service;

    @Autowired
    public PhieuTamUngController(PhieuTamUngService service) {
        this.service = service;
    }

    @PostMapping("list")
    private BaseResponseDto<PhieuTamUng> find(@RequestBody BaseRequestDto formData) throws ExecutionException, InterruptedException {
        return service.find(formData);
    }

    @GetMapping("phan-tram")
    private float tinhPhanTramTamUng(@RequestParam("maBA") String maBA,@RequestParam("giaTri") Float giaTri) {
        return service.tinhPhanTramTamUng(maBA,giaTri);
    }

    @GetMapping("{id}")
    private PhieuTamUng findById(@PathVariable("id") String id) {
        return service.findById(id);
    }

//    @PostMapping("search")
//    private BaseResponseDto<PhieuTamUng> search(@RequestBody BaseRequestDto formData) throws ExecutionException, InterruptedException {
//        return service.search(formData);
//    }

    @PostMapping
    private PhieuTamUng create(@RequestBody CreatePhieuTamUngDto formData) {
        return service.create(formData);
    }

    @PutMapping("{id}")
    private PhieuTamUng update(@PathVariable("id") String id, @RequestBody UpdatePhieuTamUngDto formData) {
        return service.update(id, formData);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    private void delete(@PathVariable("id") String id) {
        service.delete(id);
    }


}
