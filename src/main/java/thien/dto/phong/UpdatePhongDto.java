package thien.dto.phong;

import lombok.Data;
import thien.entities.Khoa;
import thien.entities.Phong;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UpdatePhongDto extends CreatePhongDto{
    @NotNull
    protected String soPhong;
    @NotNull
    private String maKhoa;

    public Phong toEntityUpdate(Phong oldEntity) {
        oldEntity.setSoPhong(soPhong);
        Khoa khoa = new Khoa();
        khoa.setMaKhoa(maKhoa);
        oldEntity.setKhoa(khoa);
        return oldEntity;
    }
}
