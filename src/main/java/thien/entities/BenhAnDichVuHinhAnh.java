package thien.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "`CTDV_HINHANH`")
@Setter
@Getter
public class BenhAnDichVuHinhAnh {
    @Id
    @Column(name = "`ID`",  columnDefinition = "VARCHAR(255)")
    private String id;

    @Column(name = "`HINHANH`")
    private String hinhAnh;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "`CTDV_ID`")
    private BenhAnDichVu benhAnDichVu;
}
