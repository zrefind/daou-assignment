package org.kang.assignment.web.controller;

import io.github.bucket4j.Bucket;
import lombok.RequiredArgsConstructor;
import org.kang.assignment.common.exception.CustomException;
import org.kang.assignment.common.exception.ErrorCode;
import org.kang.assignment.service.SettlementService;
import org.kang.assignment.web.dto.settlement.SettlementRequest;
import org.kang.assignment.web.dto.settlement.SettlementResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/settlement")
public class SettlementController {

    private static final Logger logger = LoggerFactory.getLogger(SettlementController.class);

    private final Bucket bucket;
    private final SettlementService settlementService;

    @GetMapping("/search/{factor}/{from}/{to}")
    public ResponseEntity<SettlementResponse> findByPeriod(@PathVariable String factor, @PathVariable String from, @PathVariable String to) {
        if (bucket.tryConsume(1)) {
            logger.debug("bucket remains: {}", bucket.getAvailableTokens());
            return ResponseEntity.ok(settlementService.findByPeriod(factor, from, to));
        }

        throw new CustomException(ErrorCode.BUCKET_HAS_EXHAUSTED);
    }

    @PostMapping("/enrollment")
    public ResponseEntity<SettlementResponse> enrollment(@RequestBody SettlementRequest request) {
        if (bucket.tryConsume(1)) {
            logger.debug("bucket remains: {}", bucket.getAvailableTokens());
            return ResponseEntity.ok(settlementService.enrollment(request));
        }

        throw new CustomException(ErrorCode.BUCKET_HAS_EXHAUSTED);
    }

    @PutMapping("/correction")
    public ResponseEntity<SettlementResponse> correction(@RequestBody SettlementRequest request) {
        if (bucket.tryConsume(1)) {
            logger.debug("bucket remains: {}", bucket.getAvailableTokens());
            return ResponseEntity.ok(settlementService.correction(request));
        }

        throw new CustomException(ErrorCode.BUCKET_HAS_EXHAUSTED);
    }

    @DeleteMapping("/elimination")
    public ResponseEntity<SettlementResponse> elimination(@RequestBody SettlementRequest request) {
        if (bucket.tryConsume(1)) {
            logger.debug("bucket remains: {}", bucket.getAvailableTokens());
            return ResponseEntity.ok(settlementService.elimination(request));
        }

        throw new CustomException(ErrorCode.BUCKET_HAS_EXHAUSTED);
    }

}
