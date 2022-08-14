package thien.dto.benhan;

import lombok.Data;
import org.apache.commons.lang3.time.DateUtils;
import thien.entities.BenhAn;
import thien.entities.BenhNhan;
import thien.entities.NhanVien;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

@Data
public class CreateBenhAnDto {

    @NotNull
    protected String maNV;

    @NotNull
    protected String maBacSi;

    protected String maYta;

    @NotNull
    @NotBlank
    @Size(min = 9, max = 15)
    protected String cmnd;

    @NotNull
    protected Float chieuCao;

    @NotNull
    protected Float canNang;

    @NotNull
    @NotBlank
    @Size(min = 10, max = 1000)
    protected String chanDoan;

    @NotNull
    @NotBlank
    @Size(min = 10, max = 50)
    protected String tinhTrang;

    @NotNull
    protected Date ngayKham;

    protected String tienSu;


    public BenhAn toEntity() {
        BenhAn entity = new BenhAn();
        handleField(entity);
        BenhNhan benhNhan = new BenhNhan();
        benhNhan.setCmnd(cmnd);
        entity.setBenhNhan(benhNhan);

        NhanVien nhanVien = new NhanVien();
        nhanVien.setMaNV(maNV);
        entity.setNhanVien(nhanVien);
        String maBAgenerate = generateMaBenhAn();
        entity.setMaBA(maBAgenerate);

        return entity;
    }

    public String generateMaBenhAn() {
        Date date = new Date();
        String strDate = (new SimpleDateFormat("dd-MM-yyyy hh:mm:ss")).format(date).replace("-","")
                .replace(" ", "")
                .replace(":","");
        int start = cmnd.length()-5;
        int end = cmnd.length();
        String cmndHandle = cmnd.substring(start, end);
        return "BA"+strDate+"CM"+cmndHandle;
    }

    public void handleField(BenhAn entity){
        entity.setCanNang(canNang);
        entity.setChieuCao(chieuCao);
        entity.setNgayLap(new Date());
        entity.setTienSu(tienSu != null ? tienSu : null );
    };
}
