package thien.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@MappedSuperclass
@Getter
@Setter
public class BasePerson extends BaseEntity{
    @Column(name="`HOTEN`", columnDefinition = "VARCHAR(50)", nullable = false)
    protected String hoTen;

    @Column(name="`GIOITINH`", columnDefinition = "VARCHAR(5)", nullable = false)
    protected String gioiTinh;

    @Column(name="`NGAYSINH`", nullable = false)
    protected Date ngaySinh;

    @Column(name="`DIACHI`", columnDefinition = "VARCHAR(100)", nullable = false)
    protected String diaChi;

    @Column(name="`HINHANH`", columnDefinition = "VARCHAR(255)", nullable = false)
    protected String hinhAnh;
}
