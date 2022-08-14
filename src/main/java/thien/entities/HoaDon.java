package thien.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(
        name = "`HOADON`"
)
@Getter
@Setter
public class HoaDon extends BaseEntity {
    @Id
    @Column(name = "`MAHD`", columnDefinition = "VARCHAR(25)")
    private String maHD;

    @Column(name = "`NGAYLAP`", nullable = false)
    private Date ngayLap;

    @Column(name = "`TIENTHUOC`", columnDefinition = "DOUBLE PRECISION")
    private Float tienThuoc;

    @Column(name = "`TIENDICHVU`", columnDefinition = "DOUBLE PRECISION")
    private Float tienDichVu;

    @Column(name = "`TIENGIUONG`", columnDefinition = "DOUBLE PRECISION")
    private Float tienGiuong;

    @Column(name = "`TONGTAMUNG`", columnDefinition = "DOUBLE PRECISION")
    private Float tongTamUng;

    @Column(name = "`TONGTIEN`", columnDefinition = "DOUBLE PRECISION")
    private Float tongTien;

    @Column(name = "`THUCTRA`", columnDefinition = "DOUBLE PRECISION")
    private Float thucTra;

    @Column(name = "`GHICHU`", columnDefinition = "VARCHAR(255)")
    private String ghiChu;

    @JsonManagedReference
    @OneToOne
    @JoinColumn(name = "`MABA`", nullable = false)
    private BenhAn benhAn;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "`MANV`")
    private NhanVien nhanVien;

}
