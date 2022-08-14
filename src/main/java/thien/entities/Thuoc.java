package thien.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "`THUOC`")
@Getter
@Setter
public class Thuoc extends BaseEntity {
    @Id
    @Column(name = "`MATHUOC`", columnDefinition = "VARCHAR(25)")
    private String maThuoc;

    @Column(name = "`TENTHUOC`", columnDefinition = "VARCHAR(100)", unique = true, nullable = false)
    private String tenThuoc;

    @Column(name = "`CONGDUNG`", columnDefinition = "VARCHAR(255)", nullable = false)
    private String congDung;

    @Column(name = "`MOTA`", columnDefinition = "VARCHAR(255)")
    private String moTa;

    @Column(name = "`HINHANH`", columnDefinition = "VARCHAR(255)")
    private String hinhAnh;

    @JsonBackReference
    @OneToMany(mappedBy = "thuoc", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST})
    private List<ThuocCapNhatGia> listThuocCapNhatGias = new ArrayList<>();

    @JsonBackReference
    @OneToMany(mappedBy = "thuoc", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST})
    private List<ToaThuocChiTiet> toaThuocChiTietList = new ArrayList<>();

}
