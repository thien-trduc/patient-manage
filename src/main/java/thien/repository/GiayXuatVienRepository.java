package thien.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import thien.entities.GiayXuatVien;
import thien.entities.HoaDon;

import java.util.List;

@Repository
public interface GiayXuatVienRepository extends JpaRepository<GiayXuatVien,String>, JpaSpecificationExecutor<GiayXuatVien> {
    List<GiayXuatVien> findByBenhAn_MaBA(String maBA, Pageable pageable);
    Integer countByBenhAn_MaBA(String maBA);
}
