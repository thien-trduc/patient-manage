package thien.service;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import thien.dto.BaseRequestDto;
import thien.util.Constant;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.text.DecimalFormat;
import java.util.Map;
import java.util.UUID;

@Component
@Log4j2
public class UtilService {
    public Pageable paginate(BaseRequestDto formData) {
        int page = formData.getPageIndex() != null ? (formData.getPageIndex() - 1) : Constant.PAGE_INDEX;
        int size = formData.getPageSize() != null ? formData.getPageSize() : Constant.PAGE_SIZE;
        Sort sortable = null;
        Pageable pageable = null;
        if (formData.getSort() != null && !formData.getSort().isEmpty()) {
            for (Map.Entry<String, String> entry : formData.getSort().entrySet()) {
                sortable = StringUtils.equals(entry.getValue(), "ASC") ? Sort.by(entry.getKey()).ascending() : Sort.by(entry.getKey()).descending();
            }
            pageable = PageRequest.of(page, size, sortable);
        } else {
            pageable = PageRequest.of(page, size);
        }
        return pageable;
    }

    public HttpHeaders handleHeaderResponseFile(HttpServletRequest httpServletRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Access-Control-Allow-Methods", "POST,GET,OPTIONS,DELETE,PUT,HEAD,PATCH");
        headers.add("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        headers.add("Content-Type", "application/json;charset=utf-8");
        return headers;
    }

    public String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public JsonElement valueByKeyJson(Map<String, Object> map, String inputKey) {
        JsonObject jsonObject = getJsonFromMap(map);
        return jsonObject.get(inputKey);
    }

    public JsonObject getJsonFromMap(Map<String, Object> map) {
        Gson gson = new Gson();
        String json = gson.toJson(map);
        System.out.println(json);
        JsonParser parser = new JsonParser();
        JsonObject jsonData = parser.parse(json).getAsJsonObject();
        return jsonData;
    }

    public String readFileAsString(String pathFromResource) throws IOException {
        StringBuilder resultStringBuilder = new StringBuilder();
        try {
            File file = ResourceUtils.getFile("classpath:"+pathFromResource);
            InputStream inputStream = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
            }
        } catch (IOException e) {
            log.error(e.getMessage());
            throw e;
        }
        String data = resultStringBuilder.toString();
        return data;
    }

    public void createCell(Row row, int columnCount, Object value, CellStyle style, XSSFSheet sheet) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Float) {
            cell.setCellValue((Float) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        }else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    public String formatCurrency(Float tien) {
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        return formatter.format(tien) + " VND";
    }
}
