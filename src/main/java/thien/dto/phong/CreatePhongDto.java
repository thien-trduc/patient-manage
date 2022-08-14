package thien.dto.phong;

import lombok.Data;
import thien.entities.Khoa;
import thien.entities.Phong;

import javax.validation.constraints.NotNull;

@Data
public class CreatePhongDto {
    @NotNull
    private String soPhong;
    @NotNull
    private String maKhoa;

    @NotNull
    protected Float gia;
    protected String ghiChu;

    public Phong toEntity() {
        Phong entity = new Phong();
        entity.setSoPhong(soPhong);
        Khoa khoa = new Khoa();
        khoa.setMaKhoa(maKhoa);
        entity.setKhoa(khoa);
        return entity;
    }

}
