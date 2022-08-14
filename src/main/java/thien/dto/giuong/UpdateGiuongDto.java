package thien.dto.giuong;

import thien.entities.Giuong;

public class UpdateGiuongDto extends CreateGiuongDto {
    public Giuong toUpdateEntity (Giuong entity) {
        entity.setSoGiuong(soGiuong);
        return entity;
    }
}
