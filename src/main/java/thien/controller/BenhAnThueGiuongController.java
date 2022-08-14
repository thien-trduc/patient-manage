package thien.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import thien.dto.BaseRequestDto;
import thien.dto.BaseResponseDto;
import thien.dto.benhan.BenhAnDichVuDto;
import thien.dto.benhan.CreateBenhAnXepGiuongDto;
import thien.dto.benhan.UpdateBenhAnDichVuDto;
import thien.dto.benhan.UpdateBenhAnXepGiuongDto;
import thien.entities.BenhAnDichVu;
import thien.entities.BenhAnXepGiuong;
import thien.service.BenhAnXepGiuongService;

import java.text.ParseException;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("thue-giuong")
public class BenhAnThueGiuongController {
    BenhAnXepGiuongService service;

    @Autowired
    public BenhAnThueGiuongController(BenhAnXepGiuongService service) {
        this.service = service;
    }

    @PostMapping
    private BenhAnXepGiuong create(@RequestBody CreateBenhAnXepGiuongDto formData) {
        return service.create(formData);
    }

    @PatchMapping
    private BenhAnXepGiuong update(
            @RequestParam("idCtGiuong") Integer idCtGiuong,
            @RequestParam("maBA") String maBA,
            @RequestBody UpdateBenhAnXepGiuongDto formData
    ) {
        return service.update(maBA,idCtGiuong ,formData);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private void delete(
            @RequestParam("idCtGiuong") Integer idCtGiuong,
            @RequestParam("maBA") String maBA
    ) {
        service.delete(maBA, idCtGiuong);
    }

    @PostMapping("search")
    @ResponseStatus(value = HttpStatus.OK)
    public BaseResponseDto<BenhAnXepGiuong> searchByDate(@RequestBody BaseRequestDto formData) throws ExecutionException, InterruptedException, ParseException {
        return service.searchByDate(formData);
    }

    @PostMapping("list")
    @ResponseStatus(value = HttpStatus.OK)
    public BaseResponseDto<BenhAnXepGiuong> find(@RequestBody BaseRequestDto formData) throws ExecutionException, InterruptedException {
        return service.find(formData);
    }

    @GetMapping()
    public BenhAnXepGiuong findById(@RequestParam("idCtGiuong") Integer idCtGiuong,
                                    @RequestParam("maBA") String maBA) {
        return service.findById(maBA,idCtGiuong);
    }
}
