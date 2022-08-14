package thien.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import thien.service.FileService;
import thien.service.UtilService;
import thien.util.Constant;
import thien.util.file.FileInfo;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequestMapping("upload")
@CrossOrigin(value = "http://localhost:4200")
public class FileController {
    FileService service;
    UtilService utilService;

    private final String nhanVienBucket = Constant.NHAN_VIEN_BUCKET;
    private final String thuocBucket = Constant.THUOC_BUCKET;
    private final  String benhNhanBucket = Constant.BENH_NHAN_BUCKET;
    private final String dichVuBucket = Constant.DICH_VU_BUCKET;
    private final String dichVuChiTietBucket = Constant.DICH_VU_CHI_TIET_BUCKET;

    @Autowired
    public FileController(FileService service, UtilService utilService) {
        this.service = service;
        this.utilService = utilService;
    }

    @PostMapping(value = "nhan-vien", produces = "multipart/form-data")
    public ResponseEntity<FileInfo> uploadFileNhanVien(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        String fileName = service.save(nhanVienBucket, file);
        System.out.println("vao day");
        FileInfo fileInfo = new FileInfo();
        fileInfo.setUrl(
                MvcUriComponentsBuilder
                        .fromMethodName(FileController.class, "getFileNhanVien", fileName).build().toString()
        );
        return new ResponseEntity<>(
                fileInfo,
                this.utilService.handleHeaderResponseFile(request),
                HttpStatus.OK
        );
    }

    @GetMapping("nhan-vien/{filename:.+}")
    public ResponseEntity<byte[]> getFileNhanVien(@PathVariable String filename) throws IOException {
        Resource file = service.load(nhanVienBucket, filename);
        byte[] bytes = StreamUtils.copyToByteArray(file.getInputStream());
        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_JPEG)
                .contentType(MediaType.IMAGE_PNG)
                .body(bytes);
    }

    @PostMapping(value = "thuoc", produces = "multipart/form-data")
    public ResponseEntity<FileInfo> uploadFileThuoc(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        String fileName = service.save(thuocBucket, file);
        FileInfo fileInfo = new FileInfo();
        fileInfo.setUrl(
                MvcUriComponentsBuilder
                        .fromMethodName(FileController.class, "getFileThuoc", fileName).build().toString()
        );
        return new ResponseEntity<>(
                fileInfo,
                this.utilService.handleHeaderResponseFile(request),
                HttpStatus.OK
        );
    }

    @GetMapping("thuoc/{filename:.+}")
    public ResponseEntity<byte[]> getFileThuoc(@PathVariable String filename) throws IOException {
        Resource file = service.load(thuocBucket, filename);
        byte[] bytes = StreamUtils.copyToByteArray(file.getInputStream());
        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_JPEG)
                .contentType(MediaType.IMAGE_PNG)
                .body(bytes);
    }

    @PostMapping(value = "benh-nhan", produces = "multipart/form-data")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<FileInfo> uploadFileBenhNhan(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        String fileName = service.save(benhNhanBucket, file);
        FileInfo fileInfo = new FileInfo();
        fileInfo.setUrl(
                MvcUriComponentsBuilder
                        .fromMethodName(FileController.class, "getFileBenhNhan", fileName).build().toString()
        );
        return new ResponseEntity<>(
                fileInfo,
                this.utilService.handleHeaderResponseFile(request),
                HttpStatus.OK
        );
    }

    @GetMapping("benh-nhan/{filename:.+}")
    public ResponseEntity<byte[]> getFileBenhNhan(@PathVariable String filename) throws IOException {
        Resource file = service.load(benhNhanBucket, filename);
        byte[] bytes = StreamUtils.copyToByteArray(file.getInputStream());
        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_JPEG)
                .contentType(MediaType.IMAGE_PNG)
                .body(bytes);
    }

    @PostMapping(value = "dich-vu", produces = "multipart/form-data")
    public ResponseEntity<FileInfo> uploadFileDichVu(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        String fileName = service.save(dichVuBucket, file);
        System.out.println("vao day");
        FileInfo fileInfo = new FileInfo();
        fileInfo.setUrl(
                MvcUriComponentsBuilder
                        .fromMethodName(FileController.class, "getFileDichVu", fileName).build().toString()
        );
        return new ResponseEntity<>(
                fileInfo,
                this.utilService.handleHeaderResponseFile(request),
                HttpStatus.OK
        );
    }

    @GetMapping("dich-vu/{filename:.+}")
    public ResponseEntity<byte[]> getFileDichVu(@PathVariable String filename) throws IOException {
        Resource file = service.load(dichVuBucket, filename);
        byte[] bytes = StreamUtils.copyToByteArray(file.getInputStream());
        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_JPEG)
                .contentType(MediaType.IMAGE_PNG)
                .body(bytes);
    }

    @PostMapping(value = "dich-vu-su-dung", produces = "multipart/form-data")
    public ResponseEntity<FileInfo> uploadFileDichVuChiTiet(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        String fileName = service.save(dichVuChiTietBucket, file);
        System.out.println("vao day");
        FileInfo fileInfo = new FileInfo();
        fileInfo.setUrl(
                MvcUriComponentsBuilder
                        .fromMethodName(FileController.class, "getFileDichVuChiTiet", fileName).build().toString()
        );
        return new ResponseEntity<>(
                fileInfo,
                this.utilService.handleHeaderResponseFile(request),
                HttpStatus.OK
        );
    }

    @GetMapping("dich-vu-su-dung/{filename:.+}")
    public ResponseEntity<byte[]> getFileDichVuChiTiet(@PathVariable String filename) throws IOException {
        Resource file = service.load(dichVuChiTietBucket, filename);
        byte[] bytes = StreamUtils.copyToByteArray(file.getInputStream());
        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_JPEG)
                .contentType(MediaType.IMAGE_PNG)
                .body(bytes);
    }
}
