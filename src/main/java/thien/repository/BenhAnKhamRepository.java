package thien.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import thien.entities.BenhAn;
import thien.entities.BenhAnKham;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface BenhAnKhamRepository extends JpaRepository<BenhAnKham, Integer>, JpaSpecificationExecutor<BenhAnKham> {
    List<BenhAnKham> findByBenhAn_MaBA(String maBA,Pageable pageable);

    Integer countByBenhAn_MaBA(String maBA);

    List<BenhAnKham> findByBenhAn_MaBA_AndNgayKhamBetween(String maBA, Date startNgayKham, Date endNgayKham, Pageable pageable);

    Integer countByBenhAn_MaBA_AndNgayKhamBetween(String maBA,Date startNgayKham,Date endNgayKham);

    @Query("SELECT bak FROM BenhAnKham bak inner join ToaThuoc toa ON bak.id = toa.kham.id WHERE bak.id = :maKham")
    BenhAnKham existToaThuoc(@Param("maKham") Integer maKham);
}
