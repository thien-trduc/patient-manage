package thien.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "`BENHAN`")
@Getter
@Setter
public class BenhAn extends BaseEntity {
    @Id
    @Column(name = "`MABA`", columnDefinition = "VARCHAR(25)")
    private String maBA;

    @Column(name = "`NGAYLAP`", nullable = false)
    private Date ngayLap;

    @Column(name = "`CHIEUCAO`", nullable = false)
    private Float chieuCao;

    @Column(name = "`CANNANG`", nullable = false)
    private Float canNang;

    @Column(name = "`TIENSU`", columnDefinition = "VARCHAR(500)", nullable = false)
    private String tienSu;

    @Column(name = "`TRANGTHAI`")
    private Integer trangThai;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "`MANV`")
    private NhanVien nhanVien;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "`CMND`")
    private BenhNhan benhNhan;

    @JsonBackReference
    @OneToMany(mappedBy = "benhAn", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST})
    private List<BenhAnKham> khams = new ArrayList<>();

    @JsonBackReference
    @OneToMany(mappedBy = "benhAn", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST})
    private List<BenhAnXepGiuong> benhAnXepGiuongs = new ArrayList<>();

    @JsonBackReference
    @OneToMany(mappedBy = "benhAn", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST})
    private List<BenhAnDichVu> benhAnDichVus = new ArrayList<>();

    @JsonBackReference
    @OneToMany(mappedBy = "benhAn", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST})
    private List<PhieuTamUng> phieuTamUngs = new ArrayList<>();
}
