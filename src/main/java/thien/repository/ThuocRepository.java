package thien.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import thien.entities.Thuoc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface ThuocRepository extends JpaRepository<Thuoc,String>, JpaSpecificationExecutor<Thuoc> {
    @Query(nativeQuery = true, value = "SELECT sp_lay_gia_hien_tai(:ngay,:maThuoc )")
    Float getGiaThuoc(@Param("ngay") Date ngay,@Param("maThuoc") String maThuoc);
}
