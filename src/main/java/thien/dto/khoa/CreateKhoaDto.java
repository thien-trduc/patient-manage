package thien.dto.khoa;

import lombok.Data;
import thien.entities.Khoa;
import thien.util.Constant;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class CreateKhoaDto {
    @NotNull
    @Pattern(regexp="^KH-[0-9]+$" ,message = "Bạn không nhập đúng định dạng mã khoa")
    private String maKhoa;
    @NotNull
    private String tenKhoa;
    @NotNull
    @Pattern(regexp = Constant.PHONE_REGEX)
    private String soDienThoai;
    @Email
    private String email;

    public Khoa toEntity() {
        Khoa entity = new Khoa();
        entity.setMaKhoa(maKhoa);
        entity.setTenKhoa(tenKhoa);
        entity.setSoDienThoai(soDienThoai);
        entity.setEmail(email != null ? email : null );
        return entity;
    }
}
