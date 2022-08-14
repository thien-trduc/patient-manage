package thien.authenticate;

import java.util.ArrayList;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import thien.entities.NhanVien;
import thien.entities.NhanVienUser;
import thien.service.NhanVienUserService;

@Service
@Log4j2
public class JwtUserDetailsService implements UserDetailsService {

    NhanVienUserService service;

    @Autowired
    public JwtUserDetailsService(NhanVienUserService service) {
        this.service = service;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            NhanVienUser user = service.findByUsername(username);
            if (user == null) {
                throw new UsernameNotFoundException("User not found with username: " + username);
            }
            NhanVien nv = user.getNhanVien();
            return new MyUser(user.getUsername(), user.getPassword(), user.getId(),nv.getMaNV());

        }catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }
}
