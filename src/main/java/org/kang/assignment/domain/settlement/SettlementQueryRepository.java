package org.kang.assignment.domain.settlement;

import java.util.Optional;

public interface SettlementQueryRepository {

    Long findNewbieByPeriod(String from, String to);

    Long findBolterByPeriod(String from, String to);

    Long findPaymentByPeriod(String from, String to);

    Long findUsedByPeriod(String from, String to);

    Long findSalesByPeriod(String from, String to);

    boolean existsByTime(String time);

    Optional<Settlement> findByTime(String time);

}
