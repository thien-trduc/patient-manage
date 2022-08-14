package thien.dto.phong;

import lombok.Data;
import thien.entities.Giuong;
import thien.entities.Phong;
import thien.entities.PhongGiuong;

import javax.validation.constraints.NotNull;

@Data
public class CreatePhongGiuongDto {
    @NotNull
    private Integer maPhong;
    @NotNull
    private Integer maGiuong;

    public PhongGiuong toEntity() {
        PhongGiuong entity = new PhongGiuong();
        Phong phong = new Phong();
        phong.setMaPhong(maPhong);
        entity.setPhong(phong);

        Giuong giuong = new Giuong();
        giuong.setMaGiuong(maGiuong);
        entity.setGiuong(giuong);

        entity.setTrangThai(false);

        return entity;
    }
}
