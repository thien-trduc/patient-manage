package thien.service;

import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;
import thien.util.Constant;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@Service
@Data
@Log4j2
public class FileService {

    @Autowired
    UtilService utilService;

    private Path root;

    @PostConstruct
    public void init() throws IOException {
        try{
            CompletableFuture<Path> baseBucket = this.createFolder(Constant.BASE_DIR);
            CompletableFuture<Path> bucketBenhNhan = this.createFolder(Constant.BASE_DIR + "/" + Constant.BENH_NHAN_BUCKET);
            CompletableFuture<Path> bucketNhanVien= this.createFolder(Constant.BASE_DIR + "/" + Constant.NHAN_VIEN_BUCKET);
            CompletableFuture<Path> bucketThuoc = this.createFolder(Constant.BASE_DIR + "/" + Constant.THUOC_BUCKET);
            CompletableFuture<Path> bucketDichVu = this.createFolder(Constant.BASE_DIR + "/" + Constant.DICH_VU_BUCKET);
            CompletableFuture<Path> bucketDichVuChiTiet = this.createFolder(Constant.BASE_DIR + "/" + Constant.DICH_VU_CHI_TIET_BUCKET);
            CompletableFuture.allOf(baseBucket,bucketBenhNhan, bucketNhanVien, bucketThuoc, bucketDichVu, bucketDichVuChiTiet).join();
        } catch (IOException e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @Async
    public CompletableFuture<Path> createFolder(String location) throws IOException {
        Path path = Paths.get(location);
        if(!Files.exists(path)) {
            return CompletableFuture.completedFuture(
                    Files.createDirectory(path)
            );
        }
        return CompletableFuture.completedFuture(null);
    }

    public String save(String bucketName, MultipartFile file) {
        try {
            String path = Constant.BASE_DIR + "/" + bucketName;
            String name = this.utilService.uuid() + "_" + file.getOriginalFilename();
            String pathCheckExist = path+"/"+name;
            this.root = Paths.get(path);
            if(Files.exists(Paths.get(pathCheckExist))) {
                Files.delete(Paths.get(pathCheckExist));
            };
            Files.copy(file.getInputStream(), this.root.resolve(name));
            return name;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }

    public Resource load(String bucketName, String filename) {
        try {
            String path = Constant.BASE_DIR + "/" + bucketName;
            this.root = Paths.get(path);
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            log.error(e.getMessage());
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    public void deleteAll(String bucketName) {
        String path = Constant.BASE_DIR + "/" + bucketName;
        this.root = Paths.get(path);
        FileSystemUtils.deleteRecursively(root.toFile());
    }

    public Stream<Path> loadAll(String bucketName) {
        try {
            String pathRoot = Constant.BASE_DIR + "/" + bucketName;
            this.root = Paths.get(pathRoot);
            return Files.walk(this.root, 1).filter(path -> !path.equals(this.root)).map(this.root::relativize);
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException("Could not load the files!");
        }
    }
}
