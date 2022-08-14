package thien.dto.hoadon;

import lombok.Data;
import thien.entities.BenhAn;
import thien.entities.HoaDon;
import thien.entities.NhanVien;

import javax.validation.constraints.NotNull;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class CreateHoaDonDto {
    @NotNull
    private Date ngayLap;
    private String ghiChu;
    @NotNull
    private String maBA;
    @NotNull
    private String maNV;

    public HoaDon toEntity(ThanhToanDto thanhToanDto) {
        HoaDon entity = new HoaDon();
        handleField(entity);

        BenhAn benhAn = new BenhAn();
        benhAn.setMaBA(maBA);
        entity.setBenhAn(benhAn);

        NhanVien nhanVien = new NhanVien();
        nhanVien.setMaNV(maNV);
        entity.setNhanVien(nhanVien);

        String maHD = generateMaHD();
        entity.setMaHD(maHD);

        entity.setTienThuoc(thanhToanDto.getTienthuoc());
        entity.setTienGiuong(thanhToanDto.getTiengiuong());
        entity.setTienDichVu(thanhToanDto.getTiendichvu());
        entity.setTongTamUng(thanhToanDto.getTientamung());
        entity.setTongTien(thanhToanDto.getTongtien());
        entity.setThucTra(thanhToanDto.getThuctra());

        return entity;
    }

    public void handleField(HoaDon entity) {
        entity.setNgayLap(ngayLap);
        entity.setGhiChu(ghiChu);
    }

    public String generateMaHD() {
        Date date = new Date();
        String strDate = (new SimpleDateFormat("dd-MM-yyyy hh:mm:ss")).format(date).replace("-","")
                .replace(" ", "")
                .replace(":","");
        int start = maBA.length()-6;
        int end = maBA.length();
        String maBAHandler = maBA.substring(start, end);
        return "HD"+strDate+"BA"+maBAHandler;
    }
}
