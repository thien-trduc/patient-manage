package thien.dto.benhan;

import thien.entities.BenhAnKham;

public class UpdateBenhAnKhamDto extends CreateBenhAnKhamDto{
    public UpdateBenhAnKhamDto() {}

    public UpdateBenhAnKhamDto(CreateBenhAnDto formdata, String maBenhAn) {
        super(formdata, maBenhAn);
    }

    public BenhAnKham toUpdateEntity(BenhAnKham oldEntity) {
        handleField(oldEntity);
        return oldEntity;
    }
}
