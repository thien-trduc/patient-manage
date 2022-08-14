package thien.entities;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
public class DichVuCapNhatGiaID implements Serializable {
    @NotNull
    private String dichVu;
    @NotNull
    private Date ngayCapNhat;

    public DichVuCapNhatGiaID() {
    }

    public DichVuCapNhatGiaID(String dichVu, Date ngayCapNhat) {
        this.dichVu = dichVu;
        this.ngayCapNhat = ngayCapNhat;
    }
}
