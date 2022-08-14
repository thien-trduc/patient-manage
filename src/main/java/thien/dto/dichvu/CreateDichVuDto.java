package thien.dto.dichvu;

import lombok.Data;
import thien.entities.DichVu;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

@Data
public class CreateDichVuDto {
    @NotNull
    @NotBlank
    @Size(min = 5, max = 100, message = "Tên dịch vụ không được bỏ trống")
    protected String tenDv;
    protected String hinhAnh;
    @NotNull
    protected Float gia;
    protected String ghiChu;

    public DichVu toEntity() {
        DichVu entity = new DichVu();
        entity.setTenDv(tenDv.toUpperCase());
        entity.setHinhAnh(hinhAnh != null ? hinhAnh : null);
        String maDichVu = generateMaDichVu();
        entity.setMaDV(maDichVu);
        return entity;
    }

    public String generateMaDichVu() {
        Date date = new Date();
        String strDate = (new SimpleDateFormat("dd-MM-yyyy hh:mm:ss")).format(date).replace("-","")
                .replace(" ", "")
                .replace(":","");
        String tempTempTenDichVu = tenDv.trim().toUpperCase().replace(" ", "").replace("Đ", "D");
        String nfdNormalizedString = Normalizer.normalize(tempTempTenDichVu, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        tempTempTenDichVu = pattern.matcher(nfdNormalizedString).replaceAll("");
        int start = tempTempTenDichVu.length()-4;
        int end = tempTempTenDichVu.length();
        String tenDichVuHandle = tempTempTenDichVu.substring(start, end);
        return "DV"+strDate+tenDichVuHandle;
    }
}
