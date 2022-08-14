package thien.dto.phieutamung;

import lombok.Data;
import thien.entities.BenhAn;
import thien.entities.NhanVien;
import thien.entities.PhieuTamUng;

import javax.validation.constraints.NotNull;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class CreatePhieuTamUngDto {
    @NotNull
    protected Date ngayLap;
    @NotNull
    protected Float soTien;
    @NotNull
    protected String lyDo;
    protected String ghiChu;
    @NotNull
    protected String maBA;
    @NotNull
    protected String maNV;

    public PhieuTamUng toEntity(){
        PhieuTamUng entity = new PhieuTamUng();
        entity.setMaPTU(generateMaPTU());
        entity.setNgayLap(ngayLap);
        entity.setSoTien(soTien);
        entity.setLyDo(lyDo);
        entity.setGhiChu(ghiChu);

        BenhAn benhAn = new BenhAn();
        benhAn.setMaBA(maBA);
        entity.setBenhAn(benhAn);

        NhanVien nv = new NhanVien();
        nv.setMaNV(maNV);
        entity.setNhanVien(nv);

        return entity;
    }

    public String generateMaPTU() {
        Date date = new Date();
        String strDate = (new SimpleDateFormat("dd-MM-yyyy hh:mm:ss")).format(date).replace("-","")
                .replace(" ", "")
                .replace(":","");
        int start = maBA.length()-6;
        int end = maBA.length();
        String maBAHandler = maBA.substring(start, end);
        return "PTU"+maBAHandler+"BA"+strDate;
    }

}
