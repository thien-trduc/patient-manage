package thien.dto.phieuxuatvien;

import lombok.Data;
import thien.entities.BenhAn;
import thien.entities.GiayXuatVien;
import thien.entities.NhanVien;

import javax.validation.constraints.NotNull;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class CreateGiayXuatVienDto {
    @NotNull
    private Date ngayLap;
    private String ghiChu;
    @NotNull
    private String maBA;
    @NotNull
    private String lyDo;
    @NotNull
    private String maNV;

    public GiayXuatVien toEntity() {
        GiayXuatVien entity = new GiayXuatVien();
        handleField(entity);

        BenhAn benhAn = new BenhAn();
        benhAn.setMaBA(maBA);
        entity.setBenhAn(benhAn);

        NhanVien nhanVien = new NhanVien();
        nhanVien.setMaNV(maNV);
        entity.setNhanVien(nhanVien);

        String maGxv = generateMaGxv();
        entity.setMaPXV(maGxv);

        return entity;
    }

    public void handleField(GiayXuatVien entity) {
        entity.setNgayLap(ngayLap);
        entity.setGhiChu(ghiChu);
        entity.setLyDo(lyDo);
    }

    public String generateMaGxv() {
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
