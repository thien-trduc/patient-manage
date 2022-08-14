package thien.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@Data
@RequiredArgsConstructor
public class BaseRequestDto {
    private final Integer pageIndex;
    private final Integer pageSize;
    private final Map<String, String> sort;
    private final Map<String, Object> query;
}
