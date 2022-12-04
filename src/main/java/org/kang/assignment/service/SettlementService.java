package org.kang.assignment.service;

import lombok.RequiredArgsConstructor;
import org.kang.assignment.domain.settlement.SettlementRepository;
import org.kang.assignment.util.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class SettlementService {

    private static final Logger logger = LoggerFactory.getLogger(SettlementService.class);

    private final SettlementRepository settlementRepository;

    @Transactional(readOnly = true)
    public Long findNewbieByPeriod(String from, String to) {
        Validator.validatePeriod(from, to);
        return settlementRepository.findNewbieByPeriod(from, to);
    }

    @Transactional(readOnly = true)
    public Long findBolterByPeriod(String from, String to) {
        Validator.validatePeriod(from, to);
        return settlementRepository.findBolterByPeriod(from, to);
    }

}
