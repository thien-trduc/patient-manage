package thien.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import thien.entities.ToaThuoc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import thien.util.core.QuerySpecific;

import java.net.ContentHandler;

@Repository
public interface ToaThuocRepository extends JpaRepository<ToaThuoc,String> , JpaSpecificationExecutor<ToaThuoc> {
    ToaThuoc findByKham_Id(Integer id);
}
