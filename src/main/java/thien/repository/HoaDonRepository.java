package thien.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import thien.dto.hoadon.ThanhToanDto;
import thien.entities.HoaDon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

@Repository
public interface HoaDonRepository extends JpaRepository<HoaDon, String>, JpaSpecificationExecutor<HoaDon> {
//    @Procedure(procedureName = "thanh_toan_vien_phi")
//    Res thanhToanVienPhi(@Param("maBA") String maBA);
    List<HoaDon> findByBenhAn_MaBA(String maBA, Pageable pageable);
    Integer countByBenhAn_MaBA(String maBA);
}
