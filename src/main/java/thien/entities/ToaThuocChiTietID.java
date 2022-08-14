package thien.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
public class ToaThuocChiTietID implements Serializable {
    @NotNull
    private String toaThuoc;
    @NotNull
    private String thuoc;

    public ToaThuocChiTietID() {}

    public ToaThuocChiTietID(String maToa , String maThuoc) {
        this.toaThuoc = maToa;
        this.thuoc = maThuoc;
    }
}
