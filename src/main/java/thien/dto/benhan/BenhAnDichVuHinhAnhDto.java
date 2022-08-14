package thien.dto.benhan;

import lombok.Getter;
import lombok.Setter;
import thien.entities.BenhAnDichVuHinhAnh;

@Getter
@Setter
public class BenhAnDichVuHinhAnhDto {
    private String id;
    private String hinhAnh;
    private String name;

    public BenhAnDichVuHinhAnhDto(BenhAnDichVuHinhAnh entity) {
        this.id = entity.getId();
        this.hinhAnh = entity.getHinhAnh();
        if (hinhAnh.length() > 0) {
            String[] arrHinhAnh =  entity.getHinhAnh().split("/");
            this.name = arrHinhAnh[arrHinhAnh.length-1].replace("_", "");
        }
    }

    public BenhAnDichVuHinhAnhDto() {
    }
}
