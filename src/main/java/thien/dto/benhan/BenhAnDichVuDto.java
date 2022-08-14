package thien.dto.benhan;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import thien.entities.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class BenhAnDichVuDto {

    private String id;

    private DichVu dichVu;

    private Date ngay;

    private String ketQua;

    private Float donGia;

    private NhanVien nhanVien;

    private NhanVien bacSi;

    private List<BenhAnDichVuHinhAnhDto> benhAnDichVuHinhAnhs;

    public BenhAnDichVuDto() {
    }


    public BenhAnDichVuDto(BenhAnDichVu entity, List<BenhAnDichVuHinhAnh> benhAnDichVuHinhAnhs) {
        this.id = entity.getId();
        this.dichVu = entity.getDichVu();
        this.ngay = entity.getNgay();
        this.ketQua = entity.getKetQua();
        this.donGia = entity.getDonGia();
        this.nhanVien = entity.getNhanVien();
        this.bacSi = entity.getBacSi();
        this.benhAnDichVuHinhAnhs = benhAnDichVuHinhAnhs.stream().map(benhAnDichVuHinhAnh ->
                new BenhAnDichVuHinhAnhDto(benhAnDichVuHinhAnh)
        ).collect(Collectors.toList());
    }
}
