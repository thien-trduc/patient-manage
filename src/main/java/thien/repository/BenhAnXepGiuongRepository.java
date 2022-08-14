package thien.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import thien.entities.BenhAnDichVu;
import thien.entities.BenhAnXepGiuong;
import thien.entities.BenhAnXepGiuongID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface BenhAnXepGiuongRepository extends JpaRepository<BenhAnXepGiuong, BenhAnXepGiuongID>, JpaSpecificationExecutor<BenhAnXepGiuong> {
    List<BenhAnXepGiuong> findByBenhAn_MaBA(String maBA, Pageable pageable);
    List<BenhAnXepGiuong> findByBenhAn_MaBA(String maBA);
    Integer countByBenhAn_MaBA(String maBA);
    List<BenhAnXepGiuong> findByBenhAn_MaBA_AndNgayThueBetween(String maBA, Date start, Date end, Pageable pageable);
    Integer countByBenhAn_MaBA_AndNgayThueBetween(String maBA,Date start,Date end);
}
