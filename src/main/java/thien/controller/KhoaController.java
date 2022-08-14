package thien.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import thien.dto.BaseRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import thien.dto.BaseResponseDto;
import thien.dto.khoa.CreateKhoaDto;
import thien.dto.khoa.UpdateKhoaDto;
import thien.entities.Khoa;
import thien.service.KhoaService;
import thien.service.SendGridEmailService;
import thien.service.UtilService;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Log4j2
@RestController
@RequestMapping("khoa")
public class KhoaController {
    @Autowired
    SendGridEmailService sendGridEmailService;

    @Autowired
    KhoaService service;

    @Autowired
    UtilService utilService;

    @PostMapping("list")
    private BaseResponseDto<Khoa> find(@RequestBody BaseRequestDto formData) throws ExecutionException, InterruptedException {
        return this.service.find(formData);
    }
//    @GetMapping("test-mail")
//    public void testMail() throws IOException {
//        String data = utilService.readFileAsString();
//        sendGridEmailService.sendHTML("thien.d.tran@pkh.vn", "tntran496@gmail.com", "Hello World", data);
//    }

    @GetMapping("{id}")
    private Khoa findById(@PathVariable("id") String id) {
        return this.service.findById(id);
    }

    @PostMapping("search")
    private BaseResponseDto<Khoa> search(@RequestBody BaseRequestDto formData) throws ExecutionException, InterruptedException {
        return this.service.search(formData);
    }

    @PostMapping
    private Khoa create(@RequestBody CreateKhoaDto formData) {
        return this.service.create(formData);
    }

    @PutMapping("{id}")
    private Khoa update(@PathVariable("id") String id, @RequestBody UpdateKhoaDto formData) {
        return this.service.update(id, formData);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    private void delete(@PathVariable("id") String id) {
        this.service.delete(id);
    }
}
