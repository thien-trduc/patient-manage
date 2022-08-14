package thien.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import thien.entities.BenhAnDichVuHinhAnh;

import java.util.List;

@Repository
public interface BenhAnDichVuHinhAnhRepository extends JpaRepository<BenhAnDichVuHinhAnh, String>, JpaSpecificationExecutor<BenhAnDichVuHinhAnh> {
    List<BenhAnDichVuHinhAnh> findByBenhAnDichVu_Id(String id);
}
