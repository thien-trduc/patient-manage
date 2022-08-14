package thien.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import thien.dto.BaseRequestDto;
import thien.dto.BaseResponseDto;
import thien.dto.benhan.BenhAnKhamDto;
import thien.dto.benhan.CreateBenhAnKhamDto;
import thien.dto.benhan.UpdateBenhAnKhamDto;
import thien.entities.BenhAn;
import thien.entities.BenhAnKham;
import thien.service.BenhAnKhamService;

import java.text.ParseException;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("kham")
public class BenhAnKhamController {
    BenhAnKhamService benhAnKhamService;

    @Autowired
    public BenhAnKhamController(BenhAnKhamService benhAnKhamService) {
        this.benhAnKhamService = benhAnKhamService;
    }

    @PostMapping
    private BenhAnKham createKham(@RequestBody CreateBenhAnKhamDto formData) {
        return benhAnKhamService.create(formData);
    }

    @PutMapping("{id}")
    private BenhAnKham updateKham(@PathVariable("id") Integer id,@RequestBody UpdateBenhAnKhamDto formData) {
        return benhAnKhamService.update(id,formData);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private void delete(@PathVariable("id") Integer id) {
        benhAnKhamService.delete(id);
    }

    @PostMapping("search")
    @ResponseStatus(value = HttpStatus.OK)
    public BaseResponseDto<BenhAnKhamDto> searchByDate(@RequestBody BaseRequestDto formData) throws ExecutionException, InterruptedException, ParseException {
        return benhAnKhamService.searchByDate(formData);
    }

    @PostMapping("list")
    @ResponseStatus(value = HttpStatus.OK)
    public BaseResponseDto<BenhAnKhamDto> find(@RequestBody BaseRequestDto formData) throws ExecutionException, InterruptedException {
        return benhAnKhamService.find(formData);
    }

    @GetMapping("{id}")
    public BenhAnKhamDto findById(@PathVariable("id") Integer id) {
        return benhAnKhamService.findById(id);
    }
}
