package thien.dto.benhan;

import lombok.Data;
import thien.entities.BenhAn;
import thien.entities.BenhAnXepGiuong;
import thien.entities.PhongGiuong;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class UpdateBenhAnXepGiuongDto {
    @NotNull
    private Integer idCtPhongGiuong;
    @NotNull
    private Date ngayThue;
    private Date ngayTra;
    @NotNull
    private Float donGia;

    public BenhAnXepGiuong toUpdateEntity(BenhAnXepGiuong entity) {

        PhongGiuong phongGiuong = new PhongGiuong();
        phongGiuong.setId(idCtPhongGiuong);
        entity.setCtPhongGiuong(phongGiuong);

        entity.setNgayTra(ngayTra != null ? ngayTra : null);
        entity.setDonGia(donGia);
        entity.setNgayThue(ngayThue);

        return entity;
    }
}
