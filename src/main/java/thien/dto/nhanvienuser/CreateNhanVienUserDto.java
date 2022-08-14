package thien.dto.nhanvienuser;

import lombok.Data;
import thien.entities.NhanVien;
import thien.entities.NhanVienUser;
import thien.util.PasswordUtils;

import javax.validation.constraints.NotNull;

@Data
public class CreateNhanVienUserDto {
    @NotNull
    private String username;
    @NotNull
    private String password;
    @NotNull
    private String maNV;

    public CreateNhanVienUserDto() {
    }

    public CreateNhanVienUserDto(String username, String maNV) {
        this.username = username;
        this.password = "123456";
        this.maNV = maNV;
    }

    public NhanVienUser toEntity(){
        NhanVienUser entity = new NhanVienUser();
        entity.setUsername(username);
        String hashPass = PasswordUtils.hashPassword(password);
        entity.setPassword(hashPass);
        NhanVien nv = new NhanVien();
        nv.setMaNV(maNV);
        entity.setNhanVien(nv);


        System.out.println(maNV);
        return entity;
    }
}
