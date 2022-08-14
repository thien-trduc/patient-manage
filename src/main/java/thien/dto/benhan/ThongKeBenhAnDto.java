package thien.dto.benhan;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ThongKeBenhAnDto {
    private final String name;
    private final Integer value;
}
