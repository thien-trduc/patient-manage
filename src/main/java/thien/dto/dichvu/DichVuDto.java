package thien.dto.dichvu;

import lombok.Data;
import thien.dto.BaseDto;
import thien.entities.DichVu;

@Data
public class DichVuDto extends BaseDto {
    private String maDV;
    private String tenDv;
    private String hinhAnh;
    private Float gia;

    public DichVuDto(DichVu entity, Float gia) {
        createdAt = entity.getCreatedAt();
        updatedAt = entity.getUpdatedAt();
        deletedAt = entity.getDeletedAt();
        maDV = entity.getMaDV();
        tenDv = entity.getTenDv();
        hinhAnh = entity.getHinhAnh();
        this.gia = gia;
    }
}