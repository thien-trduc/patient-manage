package thien.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import thien.dto.GetThongKeBenhNhanPhongDto;
import thien.dto.GetThongKeDoanhThuDto;
import thien.dto.ThongKeBenhNhanPhongDto;
import thien.dto.ThongKeDoanhThuDto;
import thien.service.ThongKeService;

import java.util.List;

@RestController
@RequestMapping("thong-ke")
public class ThongKeController {
    private ThongKeService thongKeService;

    @Autowired
    public ThongKeController(ThongKeService thongKeService) {
        this.thongKeService = thongKeService;
    }

    @PostMapping("doanh-thu")
    public List<ThongKeDoanhThuDto> thongKeDoanhThu(@RequestBody GetThongKeDoanhThuDto formData) {
        return this.thongKeService.thongKeDoanhThu(formData);
    }

    @PostMapping("benh-nhan-phong")
    public List<ThongKeBenhNhanPhongDto> thongKeBenhNhanPhongDto(@RequestBody GetThongKeBenhNhanPhongDto formData) {
        return this.thongKeService.thongKeBenhNhanDieuTriPhong(formData);
    }
}
