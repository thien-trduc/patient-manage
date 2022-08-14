package thien.dto.nhanvien;

import thien.entities.NhanVien;

public class UpdateNhanVienDto extends CreateNhanVienDto{
    public NhanVien toUpdateEntity(NhanVien oldEntity) {
        handleField(oldEntity);
        return oldEntity;
    }
}
