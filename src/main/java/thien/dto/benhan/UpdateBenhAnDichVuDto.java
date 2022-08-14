package thien.dto.benhan;

import lombok.Data;
import thien.entities.BenhAnDichVu;
import thien.entities.NhanVien;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class UpdateBenhAnDichVuDto {

    @NotNull
    protected String maBacSi; // bac si chi dinh

    @NotNull
    protected String maNV; // nhan vien thuc hien

    protected String ketQua;
    protected List<String> hinhAnhs;

    public BenhAnDichVu toUpdateEntity(BenhAnDichVu oldEntity) {
        NhanVien nvThucHien = new NhanVien();
        nvThucHien.setMaNV(maNV);
        oldEntity.setNhanVien(nvThucHien);

        NhanVien bacSi = new NhanVien();
        bacSi.setMaNV(maBacSi);
        oldEntity.setBacSi(bacSi);

        oldEntity.setKetQua(ketQua != null ? ketQua : "");

        return  oldEntity;
    }
}
