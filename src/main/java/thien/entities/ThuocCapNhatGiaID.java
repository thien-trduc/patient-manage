package thien.entities;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
public class ThuocCapNhatGiaID implements Serializable {
    @NotNull
    private String thuoc;
    @NotNull
    private Date ngayCapNhat;

    public ThuocCapNhatGiaID() {
    }

    public ThuocCapNhatGiaID(String thuoc, Date ngayCapNhat) {
        this.thuoc = thuoc;
        this.ngayCapNhat = ngayCapNhat;
    }
}
