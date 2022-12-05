package org.kang.assignment.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kang.assignment.common.exception.CustomException;
import org.kang.assignment.common.exception.ErrorCode;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ValidatorTest {

    @Test
    @DisplayName("시간_입력값_검증")
    public void validateTime() {
        String valid = "2022120100";

        Validator.validateTime(valid);
    }

    @Test
    @DisplayName("시간_입력값_검증__문자열_길이_오류")
    public void validateTime__invalid_length() {
        String invalidLength = "202212010";

        try {
            Validator.validateTime(invalidLength);
        } catch (CustomException e) {
            assertEquals(ErrorCode.INVALID_TIME, e.getErrorCode());
        }
    }

    @Test
    @DisplayName("시간_입력값_검증__월_오류")
    public void validateTime__invalid_month() {
        String invalidMonth = "2022130000";

        try {
            Validator.validateTime(invalidMonth);
        } catch (CustomException e) {
            assertEquals(ErrorCode.INVALID_TIME, e.getErrorCode());
        }
    }

    @Test
    @DisplayName("시간_입력값_검증__일_오류")
    public void validateTime__invalid_day() {
        String invalidDay = "2022123200";

        try {
            Validator.validateTime(invalidDay);
        } catch (CustomException e) {
            assertEquals(ErrorCode.INVALID_TIME, e.getErrorCode());
        }
    }

    @Test
    @DisplayName("시간_입력값_검증__시간_오류")
    public void validateTime__invalid_hour() {
        String invalidHour = "2022120124";

        try {
            Validator.validateTime(invalidHour);
        } catch (CustomException e) {
            assertEquals(ErrorCode.INVALID_TIME, e.getErrorCode());
        }
    }

    @Test
    @DisplayName("기간_검증")
    public void validatePeriod() {
        String from = "2022120100";
        String to = "2022120110";
        Validator.validatePeriod(from, to);
    }

    @Test
    @DisplayName("기간_검증__시작값_없음")
    public void validatePeriod_missing_from() {
        try {
            Validator.validatePeriod("", "2022120110");
        } catch (CustomException e) {
            assertEquals(ErrorCode.MISSING_REQUIRED, e.getErrorCode());
        }
    }

    @Test
    @DisplayName("기간_검증__종료값_없음")
    public void validatePeriod_missing_to() {
        try {
            Validator.validatePeriod("2022120100", "");
        } catch (CustomException e) {
            assertEquals(ErrorCode.MISSING_REQUIRED, e.getErrorCode());
        }
    }

    @Test
    @DisplayName("기간_검증__시작이_종료보다_큼")
    public void validatePeriod__invalid_period() {
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

    @Test
    @DisplayName("정수값의_양수_판별")
    public void ifNonNullValidatePositive() {
        Validator.ifNonNullValidatePositive(0L, 1L, 2L);
        Validator.ifNonNullValidatePositive(null, 1L, 2L);

        try {
            Validator.ifNonNullValidatePositive(0L, -1L, 2L);
        } catch (CustomException e) {
            assertEquals(ErrorCode.NOT_POSITIVE, e.getErrorCode());
        }
    }

}
