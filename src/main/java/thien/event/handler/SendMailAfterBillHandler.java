package thien.event.handler;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import thien.entities.BenhNhan;
import thien.entities.HoaDon;
import thien.event.domain.SendMailAfterBillEvent;
import thien.service.HoaDonService;
import thien.service.SendGridEmailService;
import thien.service.UtilService;
import thien.util.Constant;

import java.io.IOException;

@Component
@Log4j2
public class SendMailAfterBillHandler {
    private UtilService utilService;
    private SendGridEmailService sendGridEmailService;
    private HoaDonService hoaDonService;

    @Autowired
    public SendMailAfterBillHandler(UtilService utilService, SendGridEmailService sendGridEmailService, HoaDonService hoaDonService) {
        this.utilService = utilService;
        this.sendGridEmailService = sendGridEmailService;
        this.hoaDonService = hoaDonService;
    }

    @Async
    @EventListener
    public void sendMailEvent(SendMailAfterBillEvent event) throws IOException {
       log.warn(event.getMaHD());
        HoaDon hoaDon = hoaDonService.findById(event.getMaHD());
        String html = utilService.readFileAsString("mail/hoa-don-v2.template.html");
        System.out.println(html);
        BenhNhan benhNhan = hoaDon.getBenhAn().getBenhNhan();
        html= html.replace("{MAHD}", hoaDon.getMaHD())
                .replace("{MABA}", hoaDon.getBenhAn().getMaBA())
                .replace("{HOTEN}",benhNhan.getHoTen())
                .replace("{DIENTHOAI}", benhNhan.getSoDienThoai())
                .replace("{TIENTHUOC}", utilService.formatCurrency(hoaDon.getTienThuoc()))
                .replace("{TIENGIUONG}",utilService.formatCurrency(hoaDon.getTienGiuong()))
                .replace("{TIENDICHVU}", utilService.formatCurrency(hoaDon.getTienDichVu()))
                .replace("{TAMUNG}", utilService.formatCurrency(hoaDon.getTongTamUng()))
                .replace("{TONGTIEN}", utilService.formatCurrency(hoaDon.getTongTien()))
                .replace("{THUCTRA}", utilService.formatCurrency(hoaDon.getThucTra()));
        sendGridEmailService.sendHTML(Constant.EMAIL_SENDER, benhNhan.getEmail(), "V/v Thanh toán hóa đơn nội trú", html);
    }
}
