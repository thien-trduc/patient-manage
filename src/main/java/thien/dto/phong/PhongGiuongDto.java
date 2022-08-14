package thien.dto.phong;

import lombok.Data;
import thien.entities.Giuong;
import thien.entities.Phong;
import thien.entities.PhongGiuong;

@Data
public class PhongGiuongDto {
    private Integer id;
    private Phong phong;
    private Giuong giuong;
    private String trangThai;
    private Float gia;

    public PhongGiuongDto(PhongGiuong entity, Float gia) {
        this.id = entity.getId();
        this.phong = entity.getPhong();
        this.giuong = entity.getGiuong();
        this.trangThai = entity.isTrangThai() ? "Đã thuê" : "Trống" ;
        this.gia = gia;
    }
}
