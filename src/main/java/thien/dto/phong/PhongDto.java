package thien.dto.phong;

import lombok.Data;
import thien.dto.BaseDto;
import thien.entities.DichVu;
import thien.entities.Khoa;
import thien.entities.Phong;

@Data
public class PhongDto extends BaseDto {
    private Integer maPhong;
    private String soPhong;
    private Float gia;
    private Khoa khoa;

    public PhongDto(Phong entity, Float gia) {
        createdAt = entity.getCreatedAt();
        updatedAt = entity.getUpdatedAt();
        deletedAt = entity.getDeletedAt();
        maPhong = entity.getMaPhong();
        soPhong = entity.getSoPhong();
        khoa = entity.getKhoa();
        this.gia = gia;
    }
}
