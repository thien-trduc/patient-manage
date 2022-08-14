package thien.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import thien.entities.DichVu;

import java.util.Date;

@Repository
public interface DichVuRepository extends JpaRepository<DichVu, String>, JpaSpecificationExecutor<DichVu> {
    @Query(nativeQuery = true, value = "SELECT sp_lay_gia_dv_hien_tai(:ngay,:maDV )")
    Float getGiaDichVu(@Param("ngay") Date ngay, @Param("maDV") String maDV);
}
