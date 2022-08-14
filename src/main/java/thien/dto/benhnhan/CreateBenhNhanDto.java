package thien.dto.benhnhan;

import lombok.Data;
import thien.entities.BenhNhan;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
public class CreateBenhNhanDto {
    @NotNull
    @Size(min = 8 , max = 15)
    @Pattern(regexp = "^[0-9]+", message = "Bạn nhập không đúng định dạng Chứng minh nhân dân !Chứng minh nhân dân phải là số!")
    protected String cmnd;
    @NotNull
    @Size(min = 8 , max = 50)
    protected String hoTen;
    @NotNull
    protected String gioiTinh;
    @NotNull
    protected Date ngaySinh;
    @NotNull
    @Size(min = 2 , max = 255)
    protected String diaChi;
    @NotNull
    @Pattern(regexp = "(84|0[3|5|7|8|9])+([0-9]{8})\b", message = "Bạn nhập không đúng định dạng Số điện thoại !Số điện thoại phải là số!")
    @Size(min = 8 , max = 15)
    protected String soDienThoai;
    @Email(message = "Bạn nhập không đúng định dạng email!")
    @Size(min = 8 , max = 15)
    protected String email;
    @Pattern(regexp = "^[0-9]+", message = "Bạn nhập không đúng định dạng bảo hiểm ! Bảo hiẻm y tế phải là số")
    @Size(min = 5 , max = 255)
    protected String bhyt;
    protected String hinhAnh;

    public BenhNhan toEntity() {
        BenhNhan entity = new BenhNhan();
        handleField(entity);
        return entity;
    }

    public void handleField(BenhNhan entity) {
        entity.setCmnd(cmnd);
        entity.setHoTen(hoTen.toUpperCase());
        entity.setGioiTinh(gioiTinh);
        entity.setNgaySinh(ngaySinh);
        entity.setDiaChi(diaChi);
        entity.setSoDienThoai(soDienThoai);
        entity.setEmail(email != null ?  email : null);
        entity.setBhyt(bhyt);
        entity.setDoiTuong("Nội trú");
        entity.setHinhAnh(hinhAnh != null ? hinhAnh : null);
    }
}
