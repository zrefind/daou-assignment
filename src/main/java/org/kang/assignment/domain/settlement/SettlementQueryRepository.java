package org.kang.assignment.domain.settlement;

public interface SettlementQueryRepository {

    Long findNewbieByPeriod(String from, String to);

    Long findBolterByPeriod(String from, String to);

}
