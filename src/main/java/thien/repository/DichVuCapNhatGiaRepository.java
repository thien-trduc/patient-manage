package thien.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import thien.entities.DichVuCapNhatGia;
import thien.entities.DichVuCapNhatGiaID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface DichVuCapNhatGiaRepository extends JpaRepository<DichVuCapNhatGia, DichVuCapNhatGiaID>, JpaSpecificationExecutor<DichVuCapNhatGia> {
    List<DichVuCapNhatGia> findByDichVu_MaDV(String maDV, Pageable pageable);
    Integer countByDichVu_MaDV(String maDV);
    void deleteByNgayCapNhatAndDichVu_MaDV(Date ngayCapNhat, String maDV);
}
