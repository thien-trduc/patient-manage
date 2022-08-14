package thien.dto.phieutamung;

import lombok.Data;
import thien.entities.PhieuTamUng;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class UpdatePhieuTamUngDto {
    @NotNull
    protected Date ngayLap;
    @NotNull
    protected Float soTien;
    @NotNull
    protected String lyDo;
    protected String ghiChu;

    public PhieuTamUng toEntityUpdate(PhieuTamUng entity) {
        entity.setNgayLap(ngayLap);
        entity.setSoTien(soTien);
        entity.setLyDo(lyDo);
        entity.setGhiChu(ghiChu);
        return entity;
    }
}
