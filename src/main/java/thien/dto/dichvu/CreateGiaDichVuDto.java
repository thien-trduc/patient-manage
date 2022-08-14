package thien.dto.dichvu;

import lombok.Data;
import thien.dto.thuoc.CreateThuocDto;
import thien.entities.DichVu;
import thien.entities.DichVuCapNhatGia;
import thien.entities.Thuoc;
import thien.entities.ThuocCapNhatGia;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class CreateGiaDichVuDto {
    @NotNull
    protected Date ngayCapNhat;
    @NotNull
    protected Float gia;
    protected String ghiChu;

    public CreateGiaDichVuDto() {}

    public CreateGiaDichVuDto(CreateDichVuDto formData, Date ngayCapNhat) {
        this.gia = formData.getGia();
        this.ghiChu = formData.getGhiChu() != null ? formData.getGhiChu() : "";
        this.ngayCapNhat = ngayCapNhat;
    }

    public DichVuCapNhatGia toEntity(String maDV) {
        DichVuCapNhatGia entity = new DichVuCapNhatGia();

        DichVu dv = new DichVu();
        dv.setMaDV(maDV);
        entity.setDichVu(dv);

        entity.setGia(gia);
        entity.setNgayCapNhat(ngayCapNhat);
        entity.setGhiChu(ghiChu);

        return entity;
    }
}
