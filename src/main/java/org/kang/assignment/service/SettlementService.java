package org.kang.assignment.service;

import lombok.RequiredArgsConstructor;
import org.kang.assignment.domain.settlement.SettlementRepository;
import org.kang.assignment.util.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class SettlementService {

    private static final Logger logger = LoggerFactory.getLogger(SettlementService.class);

    private final SettlementRepository settlementRepository;

    @Transactional(readOnly = true)
    public Map<String, Long> findNewbieByPeriod(String from, String to) {
        Validator.validatePeriod(from, to);
        return new HashMap<String, Long>() {{ put("newbie", settlementRepository.findNewbieByPeriod(from, to)); }};
    }

}
