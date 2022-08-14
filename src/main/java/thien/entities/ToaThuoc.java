package thien.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "`TOATHUOC`"
)
@Getter
@Setter
public class ToaThuoc extends BaseEntity {
    @Id
    @Column(name = "`MATOA`", columnDefinition = "VARCHAR(25)")
    private String maToa;

    @Column(name = "`THUCHIENYLENH`", columnDefinition = "VARCHAR(255)")
    private String thucHienYLenh;

    @JsonManagedReference
    @OneToOne
    @JoinColumn(name = "`CTKHAM_ID`", unique = true)
    private BenhAnKham kham;

    @JsonBackReference
    @OneToMany(mappedBy = "toaThuoc", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<ToaThuocChiTiet> toaThuocChiTietList = new ArrayList<>();

}
