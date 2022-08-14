package thien.dto.loainhanvien;

import lombok.Data;
import thien.entities.LoaiNhanVien;

import javax.validation.constraints.NotNull;

@Data
public class CreateLoaiNhanVienDto {
    @NotNull
    private String tenLoai;

    public LoaiNhanVien toEntity() {
        LoaiNhanVien entity = new LoaiNhanVien();
        entity.setTenLoai(tenLoai);
        return entity;
    }
}
