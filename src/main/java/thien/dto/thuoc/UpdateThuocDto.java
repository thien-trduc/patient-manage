package thien.dto.thuoc;

import lombok.Data;
import thien.entities.Thuoc;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UpdateThuocDto {
    @NotNull
    @NotBlank
    @Size(min = 5, max = 100, message = "Tên thuốc không được bỏ trống")
    protected String tenThuoc;
    @NotNull
    @NotBlank
    @Size(min = 5, max = 255, message = "Công dụng không được bỏ trống")
    protected String congDung;
    protected String moTa;
    protected String hinhAnh;

    public Thuoc toEntityUpdate(Thuoc oldEntity) {
        oldEntity.setTenThuoc(tenThuoc.toUpperCase());
        oldEntity.setCongDung(congDung);
        oldEntity.setMoTa(moTa != null ? moTa : null);
        oldEntity.setHinhAnh(hinhAnh != null ? hinhAnh : null);
        return oldEntity;
    }
}
