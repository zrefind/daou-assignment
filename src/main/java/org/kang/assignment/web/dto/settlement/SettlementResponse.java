package org.kang.assignment.web.dto.settlement;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.kang.assignment.common.enums.ResponseType;
import org.kang.assignment.domain.settlement.Settlement;
import org.kang.assignment.web.dto.Response;

import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SettlementResponse extends Response {

    private String time;
    private Long newbie;
    private Long bolter;
    private Long payment;
    private Long used;
    private Long sales;

    private SettlementResponse(ResponseType responseType, String time, Long newbie, Long bolter, Long payment, Long used, Long sales) {
        super(responseType);
        this.time = time;
        this.newbie = newbie;
        this.bolter = bolter;
        this.payment = payment;
        this.used = used;
        this.sales = sales;
    }

    public static SettlementResponse done(Settlement settlement) {
        String time = settlement.getTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH"));
        return new SettlementResponse(ResponseType.DONE, time, settlement.getNewbie(), settlement.getBolter(), settlement.getPayment(), settlement.getUsed(), settlement.getSales());
    }

}
