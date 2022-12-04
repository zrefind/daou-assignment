package org.kang.assignment.web.controller;

import io.github.bucket4j.Bucket;
import lombok.RequiredArgsConstructor;
import org.kang.assignment.common.exception.CustomException;
import org.kang.assignment.common.exception.ErrorCode;
import org.kang.assignment.service.SettlementService;
import org.kang.assignment.util.RateLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/settlement")
public class SettlementController {

    private static final Logger logger = LoggerFactory.getLogger(SettlementController.class);

    private final Bucket bucket = RateLimiter.generateSimpleBucket();
    private final SettlementService settlementService;

    @GetMapping("/newbie/{from}/{to}")
    public ResponseEntity<Map<String, Long>> findNewbieByPeriod(@PathVariable String from, @PathVariable String to) {
        if (bucket.tryConsume(1)) {
            logger.info("bucket remains: {}", bucket.getAvailableTokens());
            return ResponseEntity.ok(settlementService.findNewbieByPeriod(from, to));
        }

        throw new CustomException(ErrorCode.BUCKET_HAS_EXHAUSTED);
    }

}
