package thien.dto.benhnhan;


import lombok.Data;
import thien.entities.BenhNhan;

public class UpdateBenhNhanDto extends CreateBenhNhanDto{

    public BenhNhan toUpdateEntity(BenhNhan entity) {
        handleField(entity);
        return entity;
    }
}
