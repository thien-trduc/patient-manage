package thien.dto.hoadon;

import lombok.Data;
import thien.entities.HoaDon;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class UpdateHoaDonDto {
    @NotNull
    private Date ngayLap;
    private String ghiChu;

    public HoaDon toUpdateEntity(HoaDon oldEntity) {
        oldEntity.setNgayLap(ngayLap);
        oldEntity.setGhiChu(ghiChu);
        return oldEntity;
    }
}
