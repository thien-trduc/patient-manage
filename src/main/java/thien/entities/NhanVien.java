package thien.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "`NHANVIEN`")
@Getter
@Setter
public class NhanVien extends BasePerson {
    @Id
    @Column(name = "`MANV`", columnDefinition = "VARCHAR(15)")
    private String maNV;

    @Column(name = "`CHUCVU`", columnDefinition = "VARCHAR(30)", nullable = false)
    private String chucVu;

    @Column(name = "`SODIENTHOAI`", columnDefinition = "VARCHAR(20)", nullable = false, unique = true)
    private String soDienThoai;

    @Column(name = "`EMAIL`", columnDefinition = "VARCHAR(100)", nullable = false, unique = true)
    private String email;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "`MALOAI`")
    private LoaiNhanVien loaiNV;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "`MAKHOA`")
    private Khoa khoa;

    @JsonBackReference
    @OneToMany(mappedBy = "nhanVien", cascade = {CascadeType.PERSIST} ,fetch = FetchType.LAZY)
    private List<BenhAn> benhAns = new ArrayList<>();

    @JsonBackReference
    @OneToMany(mappedBy = "nhanVien", cascade = {CascadeType.PERSIST} ,fetch = FetchType.LAZY)
    private List<BenhAnDichVu> nhanVienThucHien = new ArrayList<>();

    @JsonBackReference
    @OneToMany(mappedBy = "bacSi", cascade = {CascadeType.PERSIST} ,fetch = FetchType.LAZY)
    private List<BenhAnDichVu> bacSiChiDinh = new ArrayList<>();

    @JsonBackReference
    @OneToMany(mappedBy = "nhanVien", cascade = {CascadeType.PERSIST} ,fetch = FetchType.LAZY)
    private List<HoaDon> hoaDons = new ArrayList<>();

    @JsonBackReference
    @OneToMany(mappedBy = "nhanVien", cascade = {CascadeType.PERSIST} ,fetch = FetchType.LAZY)
    private List<GiayXuatVien> giayXuatViens = new ArrayList<>();

    @JsonBackReference
    @OneToMany(mappedBy = "nhanVien", cascade = {CascadeType.PERSIST} ,fetch = FetchType.LAZY)
    private List<PhieuTamUng> phieuTamUngs = new ArrayList<>();

    @JsonBackReference
    @OneToMany(mappedBy = "nhanVien", cascade = {CascadeType.PERSIST} ,fetch = FetchType.LAZY)
    private List<NhanVienUser> nhanVienUsers = new ArrayList<>();
}
