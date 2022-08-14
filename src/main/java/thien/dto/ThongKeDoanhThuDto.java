package thien.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ThongKeDoanhThuDto {
    private String ngay;
    private Float tongTien;

    public ThongKeDoanhThuDto() {
    }

    public ThongKeDoanhThuDto(String ngay, Float tongTien) {
        this.ngay = ngay;
        this.tongTien = tongTien;
    }
}
