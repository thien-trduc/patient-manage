package thien.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "`KHOA`")
@Getter
@Setter
public class Khoa extends BaseEntity{
    @Id
    @Column(name = "`MAKHOA`", columnDefinition = "VARCHAR(15)")
    private String maKhoa;
    @Column(name = "`TENKHOA`", columnDefinition = "VARCHAR(50)", unique = true, nullable = false)
    private String tenKhoa;
    @Column(name = "`SODIENTHOAI`", columnDefinition = "VARCHAR(20)",unique = true)
    private String soDienThoai;
    @Column(name = "`EMAIL`", columnDefinition = "VARCHAR(100)",unique = true)
    private String email;
    @JsonBackReference
    @OneToMany(mappedBy = "khoa", cascade = { CascadeType.PERSIST }, fetch = FetchType.LAZY)
    private List<NhanVien> nhanViens;
    @JsonBackReference
    @OneToMany(mappedBy = "khoa", cascade = { CascadeType.PERSIST }, fetch = FetchType.LAZY)
    private List<Phong> phongs;
}
