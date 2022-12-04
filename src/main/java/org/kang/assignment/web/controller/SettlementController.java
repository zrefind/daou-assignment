package org.kang.assignment.web.controller;

import lombok.RequiredArgsConstructor;
import org.kang.assignment.service.SettlementService;
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

    private final SettlementService settlementService;

    @GetMapping("/newbie/{from}/{to}")
    public ResponseEntity<Map<String, Long>> findNewbieByPeriod(@PathVariable String from, @PathVariable String to) {
        return ResponseEntity.ok(settlementService.findNewbieByPeriod(from, to));
    }

}
