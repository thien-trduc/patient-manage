package thien.dto.thuoc;

import lombok.Data;
import thien.dto.BaseDto;
import thien.entities.Thuoc;

@Data
public class ThuocDto extends BaseDto {
    private String maThuoc;
    private String tenThuoc;
    private String congDung;
    private String moTa;
    private String hinhAnh;
    private Float gia;

    public ThuocDto(Thuoc entity, Float gia) {
        createdAt = entity.getCreatedAt();
        updatedAt = entity.getUpdatedAt();
        deletedAt = entity.getDeletedAt();
        maThuoc = entity.getMaThuoc();
        tenThuoc = entity.getTenThuoc();
        hinhAnh = entity.getHinhAnh();
        congDung = entity.getCongDung();
        moTa = entity.getMoTa();
        this.gia = gia;
    }
}
