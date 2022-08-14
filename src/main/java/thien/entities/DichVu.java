package thien.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "`DICHVU`")
@Getter
@Setter
public class DichVu extends BaseEntity {
    @Id
    @Column(name = "`MADV`", columnDefinition = "VARCHAR(25)")
    private String maDV;

    @Column(name = "`TENDV`", columnDefinition = "VARCHAR(100)", nullable = false, unique = true)
    private String tenDv;

    @Column(name = "`HINHANH`", columnDefinition = "VARCHAR(255)")
    private String hinhAnh;

    @JsonBackReference
    @OneToMany(mappedBy = "dichVu", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST})
    private List<DichVuCapNhatGia> dichVuCapNhatGiaList = new ArrayList<>();

    @JsonBackReference
    @OneToMany(mappedBy = "dichVu", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST})
    private List<BenhAnDichVu> benhAnDichVus = new ArrayList<>();

}
