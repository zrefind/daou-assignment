package org.kang.assignment.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kang.assignment.common.exception.CustomException;
import org.kang.assignment.common.exception.ErrorCode;
import org.kang.assignment.domain.settlement.Settlement;
import org.springframework.core.io.ClassPathResource;

import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class SettlementParserTest {

    private final ClassPathResource sampleResourceCsv = new ClassPathResource("sample.csv");
    private final ClassPathResource sampleResourceTxt = new ClassPathResource("sample.txt");

    @Test
    @DisplayName("CSV_파일_변환")
    public void csvToSettlement() {
        try {
            List<Settlement> settlements = SettlementParser.csvToSettlement(sampleResourceCsv.getFile());
            assertFalse(settlements.isEmpty());
            assertEquals("2022-12-01 00", settlements.get(0).getTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH")));
            System.out.println("#### success to test for csvToSettlement");
        } catch (Exception e) {
            System.out.println("#### fail to test for csvToSettlement");
            System.out.println(e.getLocalizedMessage());
        }
    }

    @Test
    @DisplayName("CSV_파일_변환__실패")
    public void csvToSettlement__fail() {
        try {
            SettlementParser.csvToSettlement(sampleResourceTxt.getFile());
        } catch (Exception e) {
            assert e instanceof CustomException;
            assertEquals(ErrorCode.INVALID_FILE, ((CustomException) e).getErrorCode());
        }
    }

    @Test
    @DisplayName("TXT_파일_변환")
    public void txtToSettlement() {
        try {
            List<Settlement> settlements = SettlementParser.txtToSettlement(sampleResourceTxt.getFile());
            assertFalse(settlements.isEmpty());
            assertEquals("2022-12-01 00", settlements.get(0).getTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH")));
            System.out.println("#### success to test for txtToSettlement");
        } catch (Exception e) {
            System.out.println("#### fail to test for txtToSettlement");
            System.out.println(e.getLocalizedMessage());
        }
    }

    @Test
    @DisplayName("TXT_파일_변환__실패")
    public void txtToSettlement__fail() {
        try {
            SettlementParser.txtToSettlement(sampleResourceCsv.getFile());
        } catch (Exception e) {
            assert e instanceof CustomException;
            assertEquals(ErrorCode.INVALID_FILE, ((CustomException) e).getErrorCode());
        }
    }

}
