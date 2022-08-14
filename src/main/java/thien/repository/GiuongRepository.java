package thien.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import thien.entities.Giuong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import thien.entities.NhanVien;

import java.util.List;

@Repository
public interface GiuongRepository extends JpaRepository<Giuong,Integer>, JpaSpecificationExecutor<Giuong> { }
