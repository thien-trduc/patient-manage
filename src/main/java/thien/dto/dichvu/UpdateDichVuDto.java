package thien.dto.dichvu;

import lombok.Data;
import thien.entities.DichVu;
import thien.entities.Thuoc;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UpdateDichVuDto {
    @NotNull
    @NotBlank
    @Size(min = 5, max = 100, message = "Tên thuốc không được bỏ trống")
    protected String tenDv;
    protected String hinhAnh;

    public DichVu toEntityUpdate(DichVu oldEntity) {
        oldEntity.setTenDv(tenDv.toUpperCase());
        oldEntity.setHinhAnh(hinhAnh);
        return oldEntity;
    }
}
