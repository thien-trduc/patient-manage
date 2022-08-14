package thien.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import thien.entities.NhanVienUser;

@Repository
public interface NhanVienUserRepository extends JpaRepository<NhanVienUser, Integer>, JpaSpecificationExecutor<NhanVienUser> {
    NhanVienUser findByUsername(String username);
}
