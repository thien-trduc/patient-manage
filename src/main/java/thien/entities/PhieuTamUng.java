package thien.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "`PHIEUTAMUNG`")
@Getter
@Setter
public class PhieuTamUng  extends BaseEntity {
    @Id
    @Column(name = "`MAPTU`", columnDefinition = "VARCHAR(25)")
    private String maPTU;

    @Column(name = "`NGAYLAP`")
    private Date ngayLap;

    @Column(name = "`SOTIEN`", columnDefinition = "DOUBLE PRECISION")
    private Float soTien;

    @Column(name = "`LYDO`", columnDefinition = "VARCHAR(500)")
    private String lyDo;

    @Column(name = "`GHICHU`", columnDefinition = "VARCHAR(255)")
    private String ghiChu;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "`MABA`")
    private BenhAn benhAn;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "`MANV`")
    private NhanVien nhanVien;

}
