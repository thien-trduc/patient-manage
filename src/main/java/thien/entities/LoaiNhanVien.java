package thien.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "`LOAINHANVIEN`")
@Getter
@Setter
public class LoaiNhanVien extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`MALOAINV`")
    private Integer maLoaiNV;
    @Column(name = "`TENLOAI`", columnDefinition = "VARCHAR(50)", nullable = false , unique = true)
    private String tenLoai;
    @JsonBackReference
    @OneToMany(mappedBy = "loaiNV", cascade = { CascadeType.PERSIST }, fetch = FetchType.LAZY)
    private List<NhanVien> nhanViens = new ArrayList<>();

}
