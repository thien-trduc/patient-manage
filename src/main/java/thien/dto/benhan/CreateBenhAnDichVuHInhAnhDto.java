package thien.dto.benhan;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import thien.entities.BenhAnDichVu;
import thien.entities.BenhAnDichVuHinhAnh;
import thien.entities.DichVu;

import java.util.List;
import java.util.UUID;

@Data
public class CreateBenhAnDichVuHInhAnhDto {
    protected String hinhAnh;
    protected String ctdvId;

    public CreateBenhAnDichVuHInhAnhDto() {}

    public CreateBenhAnDichVuHInhAnhDto(String hinhAnh, String ctdvId) {
        this.hinhAnh = hinhAnh;
        this.ctdvId = ctdvId;
    }

    public BenhAnDichVuHinhAnh toEntity() {
        BenhAnDichVuHinhAnh entity = new BenhAnDichVuHinhAnh();

        entity.setHinhAnh(hinhAnh);
        String id = UUID.randomUUID().toString().replace("-","").toUpperCase();
        entity.setId(id);

        BenhAnDichVu benhAnDichVu = new BenhAnDichVu();
        benhAnDichVu.setId(ctdvId);
        entity.setBenhAnDichVu(benhAnDichVu);

        return entity;
    }
}
