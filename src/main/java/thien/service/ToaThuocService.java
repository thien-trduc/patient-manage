package thien.service;

import lombok.extern.log4j.Log4j2;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import thien.dto.BaseRequestDto;
import thien.dto.BaseResponseDto;
import thien.dto.toathuoc.CreateToaThuocChiTietDto;
import thien.dto.toathuoc.CreateToaThuocDto;
import thien.dto.toathuoc.UpdateToaThuocChiTietDto;
import thien.dto.toathuoc.UpdateToaThuocDto;
import thien.entities.Thuoc;
import thien.entities.ToaThuoc;
import thien.entities.ToaThuocChiTiet;
import thien.entities.ToaThuocChiTietID;
import thien.repository.ToaThuocChiTietRepository;
import thien.repository.ToaThuocRepository;
import thien.util.core.QuerySpecific;
import thien.util.core.SearchSpecific;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@Log4j2
public class ToaThuocService {
    ToaThuocRepository repository;
    ToaThuocChiTietRepository toaThuocChiTietRepository;
    UtilService utilService;
    XSSFWorkbook workbook;

    @Autowired
    public ToaThuocService(ToaThuocRepository repository, ToaThuocChiTietRepository toaThuocChiTietRepository, UtilService utilService) {
        this.repository = repository;
        this.toaThuocChiTietRepository = toaThuocChiTietRepository;
        this.utilService = utilService;
    }

    public BaseResponseDto<ToaThuoc> find(BaseRequestDto formData) throws ExecutionException, InterruptedException {
        try {
            Pageable pageable = utilService.paginate(formData);
            QuerySpecific querySpecific = new QuerySpecific<Thuoc>(formData.getQuery());
            CompletableFuture<List<ToaThuoc>> list = list(querySpecific, pageable);
            CompletableFuture<Integer> count = count(querySpecific);
            CompletableFuture.allOf(list, count).join();
            return new BaseResponseDto<>(formData.getPageIndex(), formData.getPageSize(), count.get(), list.get());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    public BaseResponseDto<ToaThuoc> search(BaseRequestDto formData) throws ExecutionException, InterruptedException {
        try {
            Pageable pageable = utilService.paginate(formData);
            SearchSpecific searchSpecific = new SearchSpecific<Thuoc>(formData.getQuery());
            CompletableFuture<List<ToaThuoc>> list = like(searchSpecific, pageable);
            CompletableFuture<Integer> count = countLike(searchSpecific);
            CompletableFuture.allOf(list, count).join();
            return new BaseResponseDto<>(formData.getPageIndex(), formData.getPageSize(), count.get(), list.get());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @Transactional(rollbackFor = {Exception.class, Throwable.class}, isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
    public ToaThuoc create(CreateToaThuocDto formData) {
        try {
            ToaThuoc entity = formData.toEntity();
            ToaThuoc toa = repository.save(entity);
            formData.getChiTietThuocs()
                    .stream()
                    .forEach(chiTiet -> {
                        ToaThuocChiTiet entityChiTiet = chiTiet.toEntity(entity.getMaToa());
                        toaThuocChiTietRepository.save(entityChiTiet);
                    });
            return toa;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    public ToaThuoc update(String maToa, UpdateToaThuocDto formData) {
        try {
            ToaThuoc oldEntity = repository.findById(maToa).get();
            ToaThuoc newEntity = formData.toUpdateEntity(oldEntity);
            return repository.save(newEntity);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    public ToaThuoc findById(String maToa) {
        try {
            return repository.findById(maToa).get();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    public void delete(String maToa) {
        try {
            repository.deleteById(maToa);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    public BaseResponseDto<ToaThuocChiTiet> findChiTiets(String maToa, BaseRequestDto formData) throws ExecutionException, InterruptedException {
        try {
            Pageable pageable = utilService.paginate(formData);
            CompletableFuture<List<ToaThuocChiTiet>> list = listChiTiet(maToa, pageable);
            CompletableFuture<Integer> count = countChiTiet(maToa);
            CompletableFuture.allOf(list, count).join();
            return new BaseResponseDto<>(formData.getPageIndex(), formData.getPageSize(), count.get(), list.get());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    public ToaThuocChiTiet createChiTiet(String maToa, CreateToaThuocChiTietDto formData) {
        try {
            return toaThuocChiTietRepository.save(formData.toEntity(maToa));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    public ToaThuocChiTiet updateChiTiet(String maToa, String maThuoc, UpdateToaThuocChiTietDto formData) {
        try {
            ToaThuocChiTietID id = new ToaThuocChiTietID(maToa, maThuoc);
            ToaThuocChiTiet oldEntity = toaThuocChiTietRepository.findById(id).get();
            return toaThuocChiTietRepository.save(formData.toUpdateEntity(oldEntity));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    public void deleteChiTiet(String maToa, String maThuoc) {
        try {
            ToaThuocChiTietID id = new ToaThuocChiTietID(maToa, maThuoc);
            System.out.println(id);
            toaThuocChiTietRepository.deleteById(id);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    public ToaThuoc findByKham_Id(Integer maKham) {
        try {
            return repository.findByKham_Id(maKham);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    public void exportExcelToaThuoc(String maToa, HttpServletResponse httpServletResponse , HttpServletRequest request) throws IOException {
        try {
            workbook = new XSSFWorkbook();

            //set header http
            httpServletResponse.setContentType("application/octet-stream");
            DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
            String currentDateTime = dateFormatter.format(new Date());
            String headerKey = "Content-Disposition";
            String headerValue = "attachment; filename=" +maToa+"_"+ currentDateTime + ".xlsx";
            httpServletResponse.setHeader(headerKey, headerValue);
//            System.out.println(request.getHeader("Authorization"));
            httpServletResponse.setHeader("filename", maToa+"_"+ currentDateTime + ".xlsx");

            List<ToaThuocChiTiet> toaThuocChiTiets = toaThuocChiTietRepository.findByToaThuoc_MaToa(maToa);
            // header
            XSSFSheet sheet = workbook.createSheet(maToa);
            Row row = sheet.createRow(0);
            CellStyle style = workbook.createCellStyle();
            XSSFFont font = workbook.createFont();
            font.setBold(true);
            font.setFontHeight(16);
            style.setFont(font);
            utilService.createCell(row, 0, "STT", style, sheet);
            utilService.createCell(row, 1, "Tên thuốc", style, sheet);
            utilService.createCell(row, 2, "Số lượng", style, sheet);
            utilService.createCell(row, 3, "Đơn vị tính", style, sheet);
            utilService.createCell(row, 4, "Đơn giá", style, sheet);
            utilService.createCell(row, 5, "Thành tiền", style, sheet);
            // body
            int rowCount = 1;
            CellStyle styleBody = workbook.createCellStyle();
            XSSFFont fontBody = workbook.createFont();
            fontBody.setFontHeight(14);
            style.setFont(fontBody);
            for (ToaThuocChiTiet ct : toaThuocChiTiets) {
                Row rowBody = sheet.createRow(rowCount++);
                int columnCount = 0;
                utilService.createCell(rowBody, columnCount++, rowCount, styleBody,sheet);
                utilService.createCell(rowBody, columnCount++, ct.getThuoc().getTenThuoc(), styleBody, sheet);
                utilService.createCell(rowBody, columnCount++, ct.getSoLuong(), styleBody, sheet);
                utilService.createCell(rowBody, columnCount++, ct.getThuoc().getMoTa(), styleBody,sheet);
                utilService.createCell(rowBody, columnCount++, ct.getDonGia(), styleBody, sheet);
                utilService.createCell(rowBody, columnCount++, ct.getDonGia() * ct.getSoLuong(), styleBody, sheet);
            }
            ServletOutputStream outputStream = httpServletResponse.getOutputStream();
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @Async
    public CompletableFuture<List<ToaThuoc>> list(QuerySpecific querySpecific, Pageable pageable) {
        return CompletableFuture.completedFuture(
                ((List<ToaThuoc>) repository.findAll(querySpecific, pageable).getContent())
        );
    }

    @Async
    public CompletableFuture<Integer> count(QuerySpecific querySpecific) {
        return CompletableFuture.completedFuture(
                (int) repository.count(querySpecific)
        );
    }

    @Async
    public CompletableFuture<List<ToaThuoc>> like(SearchSpecific searchSpecific, Pageable pageable) {
        return CompletableFuture.completedFuture(
                ((List<ToaThuoc>) repository.findAll(searchSpecific, pageable).getContent())
        );
    }

    @Async
    public CompletableFuture<Integer> countLike(SearchSpecific searchSpecific) {
        return CompletableFuture.completedFuture(
                (int) repository.count(searchSpecific)
        );
    }

    @Async
    public CompletableFuture<List<ToaThuocChiTiet>> listChiTiet(String maToa, Pageable pageable) {
        return CompletableFuture.completedFuture(
                toaThuocChiTietRepository.findByToaThuoc_MaToa(maToa, pageable)
        );
    }

    @Async
    public CompletableFuture<Integer> countChiTiet(String maToa) {
        return CompletableFuture.completedFuture(
                toaThuocChiTietRepository.countByToaThuoc_MaToa(maToa)
        );
    }
}
