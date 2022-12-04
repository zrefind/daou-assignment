package org.kang.assignment.domain.settlement;

public interface SettlementQueryRepository {

    Long findNewbieByPeriod(String from, String to);

    Long findBolterByPeriod(String from, String to);

    Long findPaymentByPeriod(String from, String to);

    Long findUsedByPeriod(String from, String to);

    Long findSalesByPeriod(String from, String to);

    boolean existsByTime(String time);

}
