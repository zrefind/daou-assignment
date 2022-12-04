package org.kang.assignment.web.dto.settlement;

import lombok.*;
import org.kang.assignment.domain.settlement.Settlement;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class SettlementRequest {

    private String time;
    private Long newbie;
    private Long bolter;
    private Long payment;
    private Long used;
    private Long sales;

    public Settlement toSettlementWithNewbie() {
        return Settlement.of(time, newbie, 0L, 0L, 0L, 0L);
    }

}
