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

    @Test
    @DisplayName("이메일_검증")
    public void validateEmail() {
        String email01 = "test@test.com";
        String email02 = ".test@test.com";
        String email03 = "test.com";
        String email04 = "test@test.com.";

        Validator.validateEmail(email01);

        try {
            Validator.validateEmail(email02);
        } catch (CustomException e) {
            assertEquals(ErrorCode.INVALID_EMAIL, e.getErrorCode());
        }

        try {
            Validator.validateEmail(email03);
        } catch (CustomException e) {
            assertEquals(ErrorCode.INVALID_EMAIL, e.getErrorCode());
        }

        try {
            Validator.validateEmail(email04);
        } catch (CustomException e) {
            assertEquals(ErrorCode.INVALID_EMAIL, e.getErrorCode());
        }
    }

    @Test
    @DisplayName("비밀번호_검증")
    public void validatePassword() {
        String password01 = "1q2w3e4r!";
        String password02 = "1q2w3e4r";
        String password03 = "1";
        String password04 = "01234567890123!";

        Validator.validatePassword(password01);

        try {
            Validator.validatePassword(password02);
        } catch (CustomException e) {
            assertEquals(ErrorCode.INVALID_PASSWORD, e.getErrorCode());
        }

        try {
            Validator.validatePassword(password03);
        } catch (CustomException e) {
            assertEquals(ErrorCode.INVALID_PASSWORD, e.getErrorCode());
        }

        try {
            Validator.validatePassword(password04);
        } catch (CustomException e) {
            assertEquals(ErrorCode.INVALID_PASSWORD, e.getErrorCode());
        }
    }

}
