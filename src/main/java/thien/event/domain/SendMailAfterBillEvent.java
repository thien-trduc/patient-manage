package thien.event.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class SendMailAfterBillEvent {
    private final String maHD;
}
