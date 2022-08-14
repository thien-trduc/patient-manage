package thien.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import thien.entities.DichVuCapNhatGia;
import thien.entities.PhongCapNhatGia;
import thien.entities.PhongCapNhatGiaID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PhongCapNhatGiaRepository extends JpaRepository<PhongCapNhatGia, PhongCapNhatGiaID>, JpaSpecificationExecutor<PhongCapNhatGia> {
    List<PhongCapNhatGia> findByPhong_MaPhong(Integer maPhong, Pageable pageable);
    Integer countByPhong_MaPhong(Integer maPhong);
    void deleteByNgayCapNhatAndPhong_MaPhong(Date ngayCapNhat, Integer maPhong);
}
