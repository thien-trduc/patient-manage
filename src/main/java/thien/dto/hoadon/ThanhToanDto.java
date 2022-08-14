package thien.dto.hoadon;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.Column;
import javax.persistence.Table;
import java.sql.ResultSet;

@Data
@RequestMapping
public class ThanhToanDto {
    protected final Float tienthuoc;
    protected final Float tiengiuong;
    protected final Float tiendichvu;
    protected final Float tientamung;
    protected final Float tongtien;
    protected final Float thuctra;
}
