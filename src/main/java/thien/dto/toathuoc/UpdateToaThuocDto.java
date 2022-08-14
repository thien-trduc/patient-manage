package thien.dto.toathuoc;

import lombok.Data;
import thien.entities.ToaThuoc;

import javax.validation.constraints.NotNull;

@Data
public class UpdateToaThuocDto {
    @NotNull
    protected String thucHienYLenh;

    public ToaThuoc toUpdateEntity(ToaThuoc oldEntity){
        oldEntity.setThucHienYLenh(thucHienYLenh);
        return oldEntity;
    }
}
