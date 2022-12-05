package org.kang.assignment.service;

import lombok.RequiredArgsConstructor;
import org.kang.assignment.common.exception.CustomException;
import org.kang.assignment.common.exception.ErrorCode;
import org.kang.assignment.domain.settlement.Settlement;
import org.kang.assignment.domain.settlement.SettlementRepository;
import org.kang.assignment.util.Validator;
import org.kang.assignment.web.dto.settlement.SettlementRequest;
import org.kang.assignment.web.dto.settlement.SettlementResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@RequiredArgsConstructor
@Service
public class SettlementService {

    private static final Logger logger = LoggerFactory.getLogger(SettlementService.class);

    private final SettlementRepository settlementRepository;

    @Transactional(readOnly = true)
    public Long findByPeriod(String factor, String from, String to) {
        Validator.validatePeriod(from, to);

        switch (factor) {
            case "newbie":
                return settlementRepository.findNewbieByPeriod(from, to);
            case "bolter":
                return settlementRepository.findBolterByPeriod(from, to);
            case "payment":
                return settlementRepository.findPaymentByPeriod(from, to);
            case "used":
                return settlementRepository.findUsedByPeriod(from, to);
            case "sales":
                return settlementRepository.findSalesByPeriod(from, to);
            default:
                throw new CustomException(ErrorCode.INVALID_FACTOR);
        }
    }

    @Transactional
    public SettlementResponse enrollment(SettlementRequest request) {
        Validator.validateTime(request.getTime());
        Validator.ifNonNullValidatePositive(request.getNewbie(), request.getBolter(), request.getPayment(), request.getUsed(), request.getSales());

        if (settlementRepository.existsByTime(request.getTime()))
            throw new CustomException(ErrorCode.DUPLICATED_SETTLEMENT);

        Settlement settlement = request.toSettlement();
        settlementRepository.save(settlement);

        logger.info("success to save settlement: {}", settlement.getTime());
        return SettlementResponse.done(settlement);
    }

    @Transactional
    public SettlementResponse correction(SettlementRequest request) {
        Validator.validateTime(request.getTime());
        Validator.ifNonNullValidatePositive(request.getNewbie(), request.getBolter(), request.getPayment(), request.getUsed(), request.getSales());

        Settlement settlement = settlementRepository.findByTime(request.getTime())
                .orElseThrow(() -> new CustomException(ErrorCode.SETTLEMENT_NOT_FOUND));

        if (Objects.nonNull(request.getNewbie()))
            settlement.updateNewbie(request.getNewbie());
        if (Objects.nonNull(request.getBolter()))
            settlement.updateBolter(request.getBolter());
        if (Objects.nonNull(request.getPayment()))
            settlement.updatePayment(request.getPayment());
        if (Objects.nonNull(request.getUsed()))
            settlement.updateUsed(request.getUsed());
        if (Objects.nonNull(request.getSales()))
            settlement.updateSales(request.getSales());

        settlementRepository.save(settlement);

        logger.info("success to update settlement: {}", settlement.getTime());
        return SettlementResponse.done(settlement);
    }

}
