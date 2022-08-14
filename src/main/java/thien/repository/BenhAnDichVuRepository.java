package thien.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import thien.entities.BenhAnDichVu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface BenhAnDichVuRepository extends JpaRepository<BenhAnDichVu,String>, JpaSpecificationExecutor<BenhAnDichVu> {
    List<BenhAnDichVu> findByBenhAn_MaBA(String maBA, Pageable pageable);

    Integer countByBenhAn_MaBA(String maBA);

    List<BenhAnDichVu> findByBenhAn_MaBA_AndNgayBetween(String maBA, Date start, Date end, Pageable pageable);

    Integer countByBenhAn_MaBA_AndNgayBetween(String maBA,Date start,Date end);
}
