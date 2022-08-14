package thien.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import thien.entities.Khoa;

@Repository
public interface KhoaRepository extends JpaRepository<Khoa, String>, JpaSpecificationExecutor<Khoa> {
}
