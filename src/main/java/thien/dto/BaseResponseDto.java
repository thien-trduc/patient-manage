package thien.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class BaseResponseDto<T> {
    private final Integer pageIndex;
    private final Integer pageSize;
    private final Integer total;
    private final List<T> rows;
}
