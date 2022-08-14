package thien.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import thien.dto.BaseRequestDto;
import thien.dto.BaseResponseDto;
import thien.dto.benhan.CreateBenhAnDto;
import thien.dto.benhan.CreateBenhAnKhamDto;
import thien.dto.benhan.ThongKeBenhAnDto;
import thien.dto.benhan.UpdateBenhAnDto;
import thien.entities.BenhAn;
import thien.entities.BenhAn;
import thien.entities.BenhAnKham;
import thien.service.BenhAnKhamService;
import thien.service.BenhAnService;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("benh-an")
public class BenhAnController {
    BenhAnService benhAnService;

    @Autowired
    public BenhAnController(BenhAnService benhAnService) {
        this.benhAnService = benhAnService;
    }

    @PostMapping
    public BenhAn create(@RequestBody CreateBenhAnDto formData) {
        return benhAnService.create(formData);
    }

    @GetMapping("thong-ke")
    public List<ThongKeBenhAnDto> thongKeBenhAn() {
//        return this.benhAnService.thongKeBenhAn();
        return Arrays.asList(new ThongKeBenhAnDto("a",1));
    }

    @PostMapping("search-benh-an-cmnd")
    @ResponseStatus(value = HttpStatus.OK)
    public BaseResponseDto<BenhAn> searchByCMND(@RequestBody BaseRequestDto formData) throws ExecutionException, InterruptedException {
        return benhAnService.searchByCMND(formData);
    }

//    @PostMapping("search")
//    @ResponseStatus(value = HttpStatus.OK)
//    public BaseResponseDto<BenhAn> search(@RequestBody BaseRequestDto formData) {
//        return this.benhAnService.search(formData)
//    }

    @PostMapping("list")
    @ResponseStatus(value = HttpStatus.OK)
    private BaseResponseDto<BenhAn> find(@RequestBody BaseRequestDto formData) throws ExecutionException, InterruptedException {
        return benhAnService.find(formData);
    }


    @GetMapping("{id}")
    private BenhAn findById(@PathVariable("id") String id) {
        return benhAnService.findById(id);
    }

    @PutMapping("{id}")
    private BenhAn update(@PathVariable("id") String id, @RequestBody UpdateBenhAnDto formData) {
        return benhAnService.update(id, formData);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    private void delete(@PathVariable("id") String id) {
        benhAnService.delete(id);
    }
}
