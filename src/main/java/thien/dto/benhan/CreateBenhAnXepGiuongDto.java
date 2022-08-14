package thien.dto.benhan;

import lombok.Data;
import thien.entities.BenhAn;
import thien.entities.BenhAnXepGiuong;
import thien.entities.PhongGiuong;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class CreateBenhAnXepGiuongDto {
    @NotNull
    private Integer idCtPhongGiuong;
    @NotNull
    private Date ngayThue;
    @NotNull
    private Float donGia;
    @NotNull
    private String maBA;

    public BenhAnXepGiuong toEntity() {
        BenhAnXepGiuong entity = new BenhAnXepGiuong();

        BenhAn benhAn = new BenhAn();
        benhAn.setMaBA(maBA);
        entity.setBenhAn(benhAn);

        PhongGiuong phongGiuong = new PhongGiuong();
        phongGiuong.setId(idCtPhongGiuong);
        entity.setCtPhongGiuong(phongGiuong);

        entity.setDonGia(donGia);
        entity.setNgayThue(ngayThue);

        return entity;
    }
}
