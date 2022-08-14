package thien.dto.thuoc;

import lombok.Data;
import thien.entities.Thuoc;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

@Data
public class CreateThuocDto {
    @NotNull
    @NotBlank
    @Size(min = 5, max = 100, message = "Tên thuốc không được bỏ trống")
    protected String tenThuoc;
    @NotNull
    @NotBlank
    @Size(min = 5, max = 255, message = "Công dụng không được bỏ trống")
    protected String congDung;
    protected String moTa;
    protected String hinhAnh;
    @NotNull
    protected Float gia;
    protected String ghiChu;

    public Thuoc toEntity() {
        Thuoc entity = new Thuoc();
        entity.setTenThuoc(tenThuoc.toUpperCase());
        entity.setCongDung(congDung);
        entity.setMoTa(moTa != null ? moTa : null);
        entity.setHinhAnh(hinhAnh != null ? hinhAnh : null);
        String maThuoc = generateMaThuoc();
        entity.setMaThuoc(maThuoc);
        return entity;
    }

    public String generateMaThuoc() {
        Date date = new Date();
        String strDate = (new SimpleDateFormat("dd-MM-yyyy hh:mm:ss")).format(date).replace("-","")
                .replace(" ", "")
                .replace(":","");
        String tempTempTenThuoc = tenThuoc.trim().toUpperCase().replace(" ", "").replace("Đ", "D");
        String nfdNormalizedString = Normalizer.normalize(tempTempTenThuoc, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        tempTempTenThuoc = pattern.matcher(nfdNormalizedString).replaceAll("");
        int start = tempTempTenThuoc.length()-4;
        int end = tempTempTenThuoc.length();
        String tenThuocHandle = tempTempTenThuoc.substring(start, end);
        return "MT"+strDate+tenThuocHandle;
    }
}

