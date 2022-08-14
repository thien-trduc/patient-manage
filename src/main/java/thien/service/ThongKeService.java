package thien.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thien.dto.GetThongKeBenhNhanPhongDto;
import thien.dto.GetThongKeDoanhThuDto;
import thien.dto.ThongKeBenhNhanPhongDto;
import thien.dto.ThongKeDoanhThuDto;
import thien.dto.hoadon.ThanhToanDto;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
@Log4j2
public class ThongKeService {
    private EntityManager entityManager;

    @Autowired
    public ThongKeService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<ThongKeDoanhThuDto> thongKeDoanhThu(GetThongKeDoanhThuDto formData) {
        List<ThongKeDoanhThuDto> list = new ArrayList<>();
        try {
            Query query = entityManager.createNativeQuery("select * from sp_thong_ke_doanh_thu(:tuNgay,:denNgay)");
            query.setParameter("tuNgay", formData.getTuNgay());
            query.setParameter("denNgay", formData.getDenNgay());

            List<Object> result = (List<Object>) query.getResultList();
            Iterator itr = result.iterator();
            while (itr.hasNext()) {
                Object[] obj = (Object[]) itr.next();
                ThongKeDoanhThuDto thongKeDoanhThuDto = new ThongKeDoanhThuDto(
                        String.valueOf(obj[0]),
                        Float.valueOf(String.valueOf(obj[1]))
                );
                list.add(thongKeDoanhThuDto);
            }
            return list;
        } catch (Exception e) {
            throw e;
        }
    }

    public List<ThongKeBenhNhanPhongDto> thongKeBenhNhanDieuTriPhong(GetThongKeBenhNhanPhongDto formData) {
        List<ThongKeBenhNhanPhongDto> list = new ArrayList<>();
        try {
            Query query = entityManager.createNativeQuery("select * from sp_thong_ke_benh_nhan_dieu_tri_phong(:khoa)");
            query.setParameter("khoa", formData.getMaKhoa());

            List<Object> result = (List<Object>) query.getResultList();
            Iterator itr = result.iterator();
            while (itr.hasNext()) {
                Object[] obj = (Object[]) itr.next();
                ThongKeBenhNhanPhongDto benhNhanDieuTriPhong = new ThongKeBenhNhanPhongDto(
                        String.valueOf(obj[0]),
                        Integer.valueOf(String.valueOf(obj[1]))
                );
                list.add(benhNhanDieuTriPhong);
            }
            return list;
        } catch (Exception e) {
            throw e;
        }
    }
}
