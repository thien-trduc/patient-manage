package thien.entities;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
public class PhongCapNhatGiaID implements Serializable {
    @NotNull
    private Integer phong;
    @NotNull
    private Date ngayCapNhat;

    public PhongCapNhatGiaID() {
    }

    public PhongCapNhatGiaID(Integer phong, Date ngayCapNhat) {
        this.phong = phong;
        this.ngayCapNhat = ngayCapNhat;
    }
}
