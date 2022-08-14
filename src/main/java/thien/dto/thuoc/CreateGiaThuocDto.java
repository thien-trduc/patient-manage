package thien.dto.thuoc;

import lombok.Data;
import thien.entities.Thuoc;
import thien.entities.ThuocCapNhatGia;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class CreateGiaThuocDto {
    @NotNull
    protected Date ngayCapNhat;
    @NotNull
    protected Float gia;
    protected String ghiChu;

    public CreateGiaThuocDto() {}

    public CreateGiaThuocDto(CreateThuocDto formData, Date ngayCapNhat) {
        this.gia = formData.getGia();
        this.ghiChu = formData.getGhiChu() != null ? formData.getGhiChu() : "";
        this.ngayCapNhat = ngayCapNhat;
    }

    public ThuocCapNhatGia toEntity(String maThuoc) {
        ThuocCapNhatGia entity = new ThuocCapNhatGia();

        Thuoc thuoc = new Thuoc();
        thuoc.setMaThuoc(maThuoc);
        entity.setThuoc(thuoc);

        entity.setGia(gia);
        entity.setNgayCapNhat(ngayCapNhat);
        entity.setGhiChu(ghiChu);

        return entity;
    }
}
