package thien.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import thien.entities.HoaDon;
import thien.entities.PhieuTamUng;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhieuTamUngRepository extends JpaRepository<PhieuTamUng, String>, JpaSpecificationExecutor<PhieuTamUng> {
    List<PhieuTamUng> findByBenhAn_MaBA(String maBA, Pageable pageable);
    Integer countByBenhAn_MaBA(String maBA);
}
