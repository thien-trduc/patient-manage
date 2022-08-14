package thien.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import thien.entities.ToaThuocChiTietID;
import thien.entities.ToaThuocChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ToaThuocChiTietRepository extends JpaRepository<ToaThuocChiTiet, ToaThuocChiTietID>, JpaSpecificationExecutor<ToaThuocChiTiet> {
    List<ToaThuocChiTiet> findByToaThuoc_MaToa(String maToa);
    List<ToaThuocChiTiet> findByToaThuoc_MaToa(String maToa, Pageable pageable);
    Integer countByToaThuoc_MaToa(String maToa);
}
