package thien.dto.benhan;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import thien.entities.BenhAn;
import thien.entities.BenhAnKham;
import thien.entities.NhanVien;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
public class CreateBenhAnKhamDto {
    @NotNull
    @NotBlank
    @Size(min = 10, max = 1000)
    protected String chanDoan;
    @NotNull
    @NotBlank
    @Size(min = 5, max = 50)
    protected String tinhTrang;
    @NotNull
    protected String maBA;
    @NotNull
    protected String maBacSi;
    protected String maYta;
    @NotNull
    protected Date ngayKham;

    public CreateBenhAnKhamDto() {}

    public CreateBenhAnKhamDto(CreateBenhAnDto formdata, String maBenhAn) {
        chanDoan = formdata.getChanDoan();
        tinhTrang = formdata.getTinhTrang();
        maBacSi = formdata.getMaBacSi();
        maYta = formdata.getMaYta() != null ?  formdata.getMaYta() : null;
        ngayKham = formdata.getNgayKham();
        maBA = maBenhAn;
    }

    public BenhAnKham toEntity() {
        BenhAnKham entity = new BenhAnKham();
        handleField(entity);
        return entity;
    }

    public void handleField(BenhAnKham entity) {
        BenhAn benhAn = new BenhAn();
        benhAn.setMaBA(maBA);
        entity.setBenhAn(benhAn);

        NhanVien bacSi = new NhanVien();
        bacSi.setMaNV(maBacSi);
        entity.setBacSi(bacSi);

        if(maYta != null ){
            NhanVien yTa = new NhanVien();
            yTa.setMaNV(maYta);
            entity.setYta(yTa);
        }

        entity.setChanDoan(chanDoan);
        entity.setTinhTrang(tinhTrang);
        entity.setNgayKham(ngayKham);
    }
}
