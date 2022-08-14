package thien.dto.giuong;

import lombok.Data;
import thien.entities.Giuong;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class CreateGiuongDto {
    @NotNull
    @Pattern(regexp = "^[0-9]+", message = "Số giường phải là số !")
    protected String soGiuong;

    public Giuong toEntity() {
        Giuong entity = new Giuong();
        entity.setSoGiuong(soGiuong);
        return entity;
    }
}
