package thien.dto.toathuoc;

import lombok.Data;
import thien.entities.Thuoc;
import thien.entities.ToaThuoc;
import thien.entities.ToaThuocChiTiet;

import javax.validation.constraints.NotNull;

@Data
public class CreateToaThuocChiTietDto {
    @NotNull
    protected String maThuoc;
    @NotNull
    protected Integer soLuong;
    @NotNull
    protected String cachDung;
    @NotNull
    protected Float donGia;

    public ToaThuocChiTiet toEntity(String maToa) {
        ToaThuocChiTiet entity = new ToaThuocChiTiet();

        Thuoc thuoc = new Thuoc();
        thuoc.setMaThuoc(maThuoc);
        entity.setThuoc(thuoc);

        ToaThuoc toaThuoc = new ToaThuoc();
        toaThuoc.setMaToa(maToa);
        entity.setToaThuoc(toaThuoc);

        handleField(entity);
        return entity;
    }

    public void handleField(ToaThuocChiTiet entity) {
        entity.setDonGia(donGia);
        entity.setSoLuong(soLuong);
        entity.setCachDung(cachDung);
    }
}
