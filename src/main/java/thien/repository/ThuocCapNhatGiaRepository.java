package thien.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import thien.entities.ThuocCapNhatGia;
import thien.entities.ThuocCapNhatGiaID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ThuocCapNhatGiaRepository extends JpaRepository<ThuocCapNhatGia, ThuocCapNhatGiaID>, JpaSpecificationExecutor<ThuocCapNhatGia> {
    List<ThuocCapNhatGia> findByThuoc_MaThuoc(String maThuoc, Pageable pageable);
    Integer countByThuoc_MaThuoc(String maThuoc);
    void deleteByNgayCapNhatAndThuoc_MaThuoc(Date ngayCapNhat, String maThuoc);
}
