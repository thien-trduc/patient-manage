package thien.dto.phieuxuatvien;

import lombok.Data;
import thien.entities.GiayXuatVien;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class UpdateGiayXuatVienDto {
    @NotNull
    private Date ngayLap;
    private String ghiChu;
    @NotNull
    private String lydo;

    public GiayXuatVien toUpdateEntity(GiayXuatVien oldEntity) {
        oldEntity.setNgayLap(ngayLap);
        oldEntity.setGhiChu(ghiChu);
        oldEntity.setLyDo(ghiChu);
        return oldEntity;
    }
}
