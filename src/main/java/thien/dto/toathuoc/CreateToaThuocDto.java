package thien.dto.toathuoc;

import lombok.Data;
import thien.entities.BenhAnKham;
import thien.entities.ToaThuoc;

import javax.validation.constraints.NotNull;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Data
public class CreateToaThuocDto {
    @NotNull
    protected Integer idCtKham;
    @NotNull
    protected String thucHienYLenh;
    @NotNull
    protected List<CreateToaThuocChiTietDto> chiTietThuocs;

    public ToaThuoc toEntity(){
        ToaThuoc entity = new ToaThuoc();
        String maToa = generateMaToa();
        entity.setMaToa(maToa);
        entity.setThucHienYLenh(thucHienYLenh);

        BenhAnKham benhAnKham = new BenhAnKham();
        benhAnKham.setId(idCtKham);
        entity.setKham(benhAnKham);

        return entity;
    }

    public String generateMaToa() {
        Date date = new Date();
        String strDate = (new SimpleDateFormat("dd-MM-yyyy hh:mm:ss")).format(date).replace("-","")
                .replace(" ", "")
                .replace(":","");
        return "TT"+idCtKham+"KH"+strDate;
    }
}
