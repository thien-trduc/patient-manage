package thien.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import thien.entities.BenhAn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import thien.entities.BenhNhan;

import java.util.List;

@Repository
public interface BenhAnRepository extends JpaRepository<BenhAn,String> , JpaSpecificationExecutor<BenhAn> {
//    @Query("SELECT ba FROM BenhAn ba INNER JOIN BenhNhan bn ON bn.cmnd = ba.benhNhan.cmnd WHERE bn.cmnd LIKE %:cmnd%")
//    List<BenhAn> likeByCMND(@Param("cmnd") String cmnd, Pageable pageable);
//    @Query("SELECT COUNT(ba) FROM BenhAn ba INNER JOIN BenhNhan bn ON bn.cmnd = ba.benhNhan.cmnd WHERE bn.cmnd LIKE %:cmnd%")
//    Integer countLikeByCMND(@Param("cmnd") String cmnd);

    List<BenhAn> findByBenhNhan_CmndContaining(String cmnd, Pageable pageable);
    Integer countByBenhNhan_CmndContaining(String cmnd);
}
