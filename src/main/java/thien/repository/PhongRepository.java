package thien.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import thien.entities.Phong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface PhongRepository extends JpaRepository<Phong, Integer>, JpaSpecificationExecutor<Phong> {
    @Query(nativeQuery = true, value = "SELECT sp_lay_gia_giuong_hien_tai(:ngay,:maPhong)")
    Float getGiaPhong(@Param("ngay") Date ngay, @Param("maPhong") Integer maPhong);
}
