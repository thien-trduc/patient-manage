package thien.entities;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class BenhAnXepGiuongID implements Serializable {
    @NotNull
    private Integer ctPhongGiuong;
    @NotNull
    private String benhAn;

    public BenhAnXepGiuongID() {
    }

    public BenhAnXepGiuongID(Integer ctPhongGiuong, String benhAn) {
        this.ctPhongGiuong = ctPhongGiuong;
        this.benhAn = benhAn;
    }
}
