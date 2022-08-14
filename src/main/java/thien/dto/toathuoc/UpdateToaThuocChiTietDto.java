package thien.dto.toathuoc;

import thien.entities.ToaThuocChiTiet;

public class UpdateToaThuocChiTietDto extends CreateToaThuocChiTietDto{
    public ToaThuocChiTiet toUpdateEntity(ToaThuocChiTiet entity) {
        handleField(entity);
        return entity;
    }
}
