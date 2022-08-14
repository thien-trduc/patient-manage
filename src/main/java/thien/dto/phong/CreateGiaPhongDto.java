package thien.dto.phong;

import lombok.Data;
import thien.entities.Phong;
import thien.entities.PhongCapNhatGia;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class CreateGiaPhongDto {
    @NotNull
    protected Date ngayCapNhat;
    @NotNull
    protected Float gia;
    protected String ghiChu;

    public CreateGiaPhongDto() {}

    public CreateGiaPhongDto(CreatePhongDto formData, Date ngayCapNhat) {
        this.gia = formData.getGia();
        this.ghiChu = formData.getGhiChu() != null ? formData.getGhiChu() : "";
        this.ngayCapNhat = ngayCapNhat;
    }

    public PhongCapNhatGia toEntity(Integer maphong) {
        PhongCapNhatGia entity = new PhongCapNhatGia();

        Phong phong = new Phong();
        phong.setMaPhong(maphong);
        entity.setPhong(phong);

        entity.setGia(gia);
        entity.setNgayCapNhat(ngayCapNhat);
        entity.setGhiChu(ghiChu);

        return entity;
    }
}
