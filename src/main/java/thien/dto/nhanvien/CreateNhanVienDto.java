package thien.dto.nhanvien;

import lombok.Data;
import thien.entities.Khoa;
import thien.entities.LoaiNhanVien;
import thien.entities.NhanVien;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class CreateNhanVienDto {
    @NotNull
    protected String hoTen;
    @NotNull
    protected String gioiTinh;
    @NotNull
    protected Date ngaySinh;
    @NotNull
    protected String diaChi;
    protected String hinhAnh;
    @NotNull
    protected String maKhoa;
    @NotNull
    protected Integer maLoaiNV;
    @NotNull
    @Pattern(regexp = "(84|0[3|5|7|8|9])+([0-9]{8})\b", message = "Bạn nhập không đúng định dạng Số điện thoại !Số điện thoại phải là số!")
    protected String soDienThoai;
    @NotNull
    @Email(message = "Bạn nhập không đúng định dạng email!")
    protected String email;
    protected String chucVu;

    public NhanVien toEntity() {
        NhanVien entity = new NhanVien();
        handleField(entity);
        String maNVgenerate = generateMaNhanVien();
        entity.setMaNV(maNVgenerate);
        return entity;
    }

    public String generateMaNhanVien() {
        Date date = new Date();
        String strDate = (new SimpleDateFormat("dd-MM-yyyy hh:mm:ss")).format(date).replace("-","")
                .replace(" ", "")
                .replace(":","");
        int start = soDienThoai.length()-6;
        int end = soDienThoai.length();
        String sdtHandle = soDienThoai.substring(start, end);
        return "NV"+strDate+"DT"+sdtHandle;
    }

    public void handleField(NhanVien entity) {
        Khoa khoa = new Khoa();
        khoa.setMaKhoa(maKhoa);
        LoaiNhanVien loaiNhanVien = new LoaiNhanVien();
        loaiNhanVien.setMaLoaiNV(maLoaiNV);

        entity.setHoTen(hoTen.toUpperCase());
        entity.setGioiTinh(gioiTinh);
        entity.setNgaySinh(ngaySinh);
        entity.setDiaChi(diaChi);
        entity.setSoDienThoai(soDienThoai);
        entity.setEmail( email != null ? email : null);
        entity.setChucVu( chucVu != null ? chucVu : null);
        entity.setKhoa(khoa);
        entity.setLoaiNV(loaiNhanVien);
        entity.setHinhAnh(hinhAnh != null ? hinhAnh : null);
    }
}
