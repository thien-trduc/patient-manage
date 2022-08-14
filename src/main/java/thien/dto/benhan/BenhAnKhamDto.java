package thien.dto.benhan;

import lombok.Data;
import thien.dto.BaseDto;
import thien.entities.BenhAn;
import thien.entities.BenhAnKham;
import thien.entities.NhanVien;

import java.util.Date;

@Data
public class BenhAnKhamDto {
    private Integer id;
    private BenhAn benhAn;
    private NhanVien bacSi;
    private NhanVien yta;
    private Date ngayKham;
    private String chanDoan;
    private String tinhTrang;
    private boolean existToaThuoc;

    public BenhAnKhamDto() {

    }

    public BenhAnKhamDto(BenhAnKham entity, boolean existToaThuoc) {
        id = entity.getId();
        benhAn = entity.getBenhAn();
        bacSi = entity.getBacSi();
        yta = entity.getYta();
        ngayKham = entity.getNgayKham();
        chanDoan = entity.getChanDoan();
        tinhTrang = entity.getTinhTrang();
        this.existToaThuoc = existToaThuoc;
    }
}
