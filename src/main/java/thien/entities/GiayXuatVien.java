package thien.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(
        name = "`PHIEUXUATVIEN`"
)
@Getter
@Setter
public class GiayXuatVien extends BaseEntity {
    @Id
    @Column(name = "`MAPXV`", columnDefinition = "VARCHAR(25)")
    private String maPXV;

    @Column(name = "`NGAYLAP`", nullable = false)
    private Date ngayLap;

    @Column(name = "`LYDO`", columnDefinition = "VARCHAR(500)")
    private String lyDo;

    @Column(name = "`GHICHU`", columnDefinition = "VARCHAR(255)")
    private String ghiChu;

    @JsonManagedReference
    @OneToOne
    @JoinColumn(name = "`MABA`")
    private BenhAn benhAn;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "`MANV`")
    private NhanVien nhanVien;

}
