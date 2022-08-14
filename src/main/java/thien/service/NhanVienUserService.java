package thien.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import thien.entities.NhanVien;
import thien.entities.NhanVienUser;
import thien.repository.NhanVienRepository;
import thien.repository.NhanVienUserRepository;
import thien.util.PasswordUtils;

@Service
@Log4j2
public class NhanVienUserService {
    NhanVienUserRepository repository;
    NhanVienRepository nhanVienRepository;

    @Autowired
    public NhanVienUserService(NhanVienUserRepository repository,NhanVienRepository nhanVienRepository) {
        this.repository = repository;
        this.nhanVienRepository = nhanVienRepository;
    }

//    public NhanVien verify(String username, String password){
//        try {
//            NhanVien nv = null;
//            NhanVienUser user = repository.findByUsername(username);
//            if (user == null) {
//                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy user !");
//            }
//            boolean checkPass = PasswordUtils.checkPassword(password, user.getPassword());
//            if (checkPass) {
//                nv = nhanVienRepository.findByNhanVienUsers_Username(username);
//            }
//            return nv;
//        } catch (Exception e) {
//            log.error(e.getMessage());
//            throw e;
//        }
//    }

    public NhanVien verify(String username){
        try {
            NhanVien nv = null;
            NhanVienUser user = repository.findByUsername(username);
            if (user == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy user !");
            }
            return nv;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    public NhanVienUser findByUsername(String username){
        try {
            return repository.findByUsername(username);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }
}
