package org.kang.assignment.service.scheduler;

import lombok.RequiredArgsConstructor;
import org.kang.assignment.domain.settlement.Settlement;
import org.kang.assignment.domain.settlement.SettlementRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SettlementSchedulerService {

    private final SettlementRepository settlementRepository;

    @Transactional
    public void saveAllToDb(List<Settlement> settlements) {
        settlementRepository.saveAll(settlements);
    }

}
