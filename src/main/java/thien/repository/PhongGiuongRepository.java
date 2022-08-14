package thien.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import thien.entities.PhongCapNhatGia;
import thien.entities.PhongGiuong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PhongGiuongRepository extends JpaRepository<PhongGiuong, Integer>, JpaSpecificationExecutor<PhongGiuong> {
    List<PhongGiuong> findByPhong_MaPhong(Integer maPhong, Pageable pageable);
    Integer countByPhong_MaPhong(Integer maPhong);
    List<PhongGiuong> findByGiuong_SoGiuongContaining(String soGiuong, Pageable pageable);
    Integer countByGiuong_SoGiuongContaining(String soGiuong);
}
