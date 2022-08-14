package thien.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import thien.entities.LoaiNhanVien;

@Repository
public interface LoaiNhanVienRepository extends JpaRepository<LoaiNhanVien, Integer>, JpaSpecificationExecutor<LoaiNhanVien> {
}
