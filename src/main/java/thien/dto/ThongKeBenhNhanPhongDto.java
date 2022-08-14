package thien.dto;

import lombok.Data;

@Data
public class ThongKeBenhNhanPhongDto {
    private String phong;
    private Integer tongBenhNhan;

    public ThongKeBenhNhanPhongDto() {}

    public ThongKeBenhNhanPhongDto(String phong, Integer tongBenhNhan) {
        this.phong = phong;
        this.tongBenhNhan = tongBenhNhan;
    }
}
