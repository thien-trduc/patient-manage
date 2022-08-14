package thien.dto.benhan;

import lombok.Data;
import thien.entities.BenhAn;
import thien.entities.BenhAnDichVu;
import thien.entities.DichVu;
import thien.entities.NhanVien;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
public class CreateBenhAnDichVuDto {
    @NotNull
    protected String maDV;
    @NotNull
    protected String maBA;
    @NotNull
    protected Float donGia;
    @NotNull
    protected String maBacSi; // bac si chi dinh
    @NotNull
    protected String maNV; // nhan vien thuc hien
    protected String ketQua;
    protected List<String> hinhAnhs;

    public BenhAnDichVu toEntity() {
        BenhAnDichVu entity = new BenhAnDichVu();

        DichVu dv = new DichVu();
        dv.setMaDV(maDV);
        entity.setDichVu(dv);

        NhanVien nvThucHien = new NhanVien();
        nvThucHien.setMaNV(maNV);
        entity.setNhanVien(nvThucHien);

        NhanVien bacSi = new NhanVien();
        bacSi.setMaNV(maBacSi);
        entity.setBacSi(bacSi);

        entity.setDonGia(donGia);
        entity.setNgay(new Date());
        entity.setKetQua(ketQua != null ? ketQua : "");

        BenhAn benhAn = new BenhAn();
        benhAn.setMaBA(maBA);
        entity.setBenhAn(benhAn);

        String id = UUID.randomUUID().toString().replace("-","").toUpperCase();
        entity.setId(id);

        return entity;
    }

}
