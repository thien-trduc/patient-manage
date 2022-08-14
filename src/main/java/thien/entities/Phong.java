package thien.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "`PHONG`")
@Getter
@Setter
public class Phong extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`MAPHONG`")
    private Integer maPhong;

    @Column(name = "`SOPHONG`", columnDefinition = "VARCHAR(10)", nullable = false, unique = true)
    private String soPhong;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "`MAKHOA`")
    private Khoa khoa;

    @JsonBackReference
    @OneToMany(mappedBy = "phong", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST})
    private List<PhongCapNhatGia> phongCapNhatGiaList = new ArrayList<>();

    @JsonBackReference
    @OneToMany(mappedBy = "phong", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST})
    private List<PhongGiuong> phongGiuongs = new ArrayList<>();

}
