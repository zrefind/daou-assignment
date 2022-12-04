package org.kang.assignment.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kang.assignment.common.exception.CustomException;
import org.kang.assignment.common.exception.ErrorCode;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ValidatorTest {

    @Test
    @DisplayName("기간_검증")
    public void validatePeriod() {
        Validator.validatePeriod("2022120100", "2022120110");
    }

    @Test
    @DisplayName("기간_검증__시작값_없음")
    public void validatePeriod_invalid_from() {
        try {
            Validator.validatePeriod("", "2022120110");
        } catch (CustomException e) {
            assertEquals(ErrorCode.MISSING_REQUIRED, e.getErrorCode());
        }
    }

    @Test
    @DisplayName("기간_검증__종료값_없음")
    public void validatePeriod_invalid_to() {
        try {
            Validator.validatePeriod("2022120100", "");
        } catch (CustomException e) {
            assertEquals(ErrorCode.MISSING_REQUIRED, e.getErrorCode());
        }
    }

    @Test
    @DisplayName("기간_검증__입력값_오류")
    public void validatePeriod__invalid_input() {
        try {
            Validator.validatePeriod("123", "2022120110");
        } catch (CustomException e) {
            assertEquals(ErrorCode.INVALID_PERIOD, e.getErrorCode());
        }
    }

    @Test
    @DisplayName("기간_검증__시작이_종료보다_큼")
    public void validatePeriod__from_is_after_to() {
        try {
            Validator.validatePeriod("2022120110", "2022120100");
        } catch (CustomException e) {
            assertEquals(ErrorCode.INVALID_PERIOD, e.getErrorCode());
        }
    }

}
