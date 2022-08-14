package thien.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import thien.entities.LoaiNhanVien;
import thien.entities.NhanVien;

import java.util.List;

@Repository
public interface NhanVienRepository extends JpaRepository<NhanVien,String>, JpaSpecificationExecutor<NhanVien> {
    List<NhanVien> findByLoaiNV(LoaiNhanVien loaiNV, Pageable pageable);

    Integer countByLoaiNV(LoaiNhanVien loaiNV);

    List<NhanVien> findByLoaiNVAndHoTenContaining(LoaiNhanVien loaiNV,String hoTen, Pageable pageable);

    Integer countByLoaiNVAndHoTenContaining(LoaiNhanVien loaiNV,String hoTen);

    NhanVien findByNhanVienUsers_Username(String username);
}
