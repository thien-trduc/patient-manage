package thien.dto.benhan;

import thien.entities.BenhAn;

public class UpdateBenhAnDto extends CreateBenhAnDto {
    public BenhAn toEntityUpdate(BenhAn oldEntity) {
        handleField(oldEntity);
        return oldEntity;
    }
}
