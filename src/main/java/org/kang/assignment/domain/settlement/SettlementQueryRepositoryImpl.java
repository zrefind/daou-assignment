package org.kang.assignment.domain.settlement;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import static org.kang.assignment.domain.settlement.QSettlement.settlement;

@RequiredArgsConstructor
@Repository
public class SettlementQueryRepositoryImpl implements SettlementQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Long findNewbieByPeriod(String from, String to) {
        return queryFactory.select(settlement.newbie.sum())
                .from(settlement)
                .where(settlement.time.between(
                        LocalDateTime.parse(from, DateTimeFormatter.ofPattern("yyyyMMddHH")),
                        LocalDateTime.parse(to, DateTimeFormatter.ofPattern("yyyyMMddHH"))
                ))
                .fetchOne();
    }

    @Override
    public Long findBolterByPeriod(String from, String to) {
        return queryFactory.select(settlement.bolter.sum())
                .from(settlement)
                .where(settlement.time.between(
                        LocalDateTime.parse(from, DateTimeFormatter.ofPattern("yyyyMMddHH")),
                        LocalDateTime.parse(to, DateTimeFormatter.ofPattern("yyyyMMddHH"))
                ))
                .fetchOne();
    }

    @Override
    public Long findPaymentByPeriod(String from, String to) {
        return queryFactory.select(settlement.payment.sum())
                .from(settlement)
                .where(settlement.time.between(
                        LocalDateTime.parse(from, DateTimeFormatter.ofPattern("yyyyMMddHH")),
                        LocalDateTime.parse(to, DateTimeFormatter.ofPattern("yyyyMMddHH"))
                ))
                .fetchOne();
    }

    @Override
    public Long findUsedByPeriod(String from, String to) {
        return queryFactory.select(settlement.used.sum())
                .from(settlement)
                .where(settlement.time.between(
                        LocalDateTime.parse(from, DateTimeFormatter.ofPattern("yyyyMMddHH")),
                        LocalDateTime.parse(to, DateTimeFormatter.ofPattern("yyyyMMddHH"))
                ))
                .fetchOne();
    }

    @Override
    public Long findSalesByPeriod(String from, String to) {
        return queryFactory.select(settlement.sales.sum())
                .from(settlement)
                .where(settlement.time.between(
                        LocalDateTime.parse(from, DateTimeFormatter.ofPattern("yyyyMMddHH")),
                        LocalDateTime.parse(to, DateTimeFormatter.ofPattern("yyyyMMddHH"))
                ))
                .fetchOne();
    }

    @Override
    public boolean existsByTime(String time) {
        return Objects.nonNull(
                queryFactory.selectFrom(settlement)
                        .where(settlement.time.eq(LocalDateTime.parse(time, DateTimeFormatter.ofPattern("yyyyMMddHH"))))
                        .fetchOne()
        );
    }

}
