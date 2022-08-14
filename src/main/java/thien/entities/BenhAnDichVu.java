package thien.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "`CHITIETDICHVU`", uniqueConstraints = {
        @UniqueConstraint(
                columnNames = {"`MABA`", "`MADV`", "`NGAY`"}
        )
})
@Getter
@Setter
public class BenhAnDichVu implements Serializable {

    @Id
    @Column(name = "`ID`", columnDefinition = "VARCHAR(255)")
    private String id;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "`MABA`")
    private BenhAn benhAn;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "`MADV`")
    private DichVu dichVu;

    @Column(name = "`NGAY`")
    private Date ngay;

    @Column(name = "`KETQUA`", columnDefinition = "VARCHAR(255)")
    private String ketQua;

    @Column(name = "`DONGIA`", columnDefinition = "DOUBLE PRECISION CHECK (`DONGIA` > 0)")
    private Float donGia;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "`MANV`")
    private NhanVien nhanVien;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "`MABS`")
    private NhanVien bacSi;

    @JsonBackReference
    @OneToMany(mappedBy = "benhAnDichVu", fetch = FetchType.LAZY,  cascade = CascadeType.PERSIST)
    private List<BenhAnDichVuHinhAnh> benhAnDichVuHinhAnhs = new ArrayList<>();
}
