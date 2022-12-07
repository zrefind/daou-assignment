package org.kang.assignment.util;

import org.kang.assignment.common.exception.CustomException;
import org.kang.assignment.common.exception.ErrorCode;
import org.springframework.util.StringUtils;

import java.util.regex.Pattern;

public class Validator {

    private static final String REGEX_TIME = "\\d{4}(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[01])(0[0-9]|1[0-9]|2[0-3])";
    private static final String REGEX_EMAIL = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
    private static final String REGEX_PASSWORD = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[`~!@#$%^&*()+|=_])[A-Za-z\\d`~!@#$%^&*()+|=_]{8,16}$";

    private static final Pattern PATTERN_TIME = Pattern.compile(REGEX_TIME);
    private static final Pattern PATTERN_EMAIL = Pattern.compile(REGEX_EMAIL);
    private static final Pattern PATTERN_PASSWORD = Pattern.compile(REGEX_PASSWORD);

    public static void validateTime(String time) {
        isValidString(PATTERN_TIME, time, ErrorCode.INVALID_TIME);
    }

    public static void validatePeriod(String from, String to) {
        validateTime(from);
        validateTime(to);

        if (Integer.parseInt(from) > Integer.parseInt(to))
            throw new CustomException(ErrorCode.INVALID_PERIOD);
    }

    public static void validateEmail(String email) {
        isValidString(PATTERN_EMAIL, email, ErrorCode.INVALID_EMAIL);
    }

    public static void validatePassword(String password) {
        isValidString(PATTERN_PASSWORD, password, ErrorCode.INVALID_PASSWORD);
    }

    public static void ifNonNullValidatePositive(Long... numbers) {
        for (Long number : numbers) {
            if (number == null)
                continue;
            if (number < 0L)
                throw new CustomException(ErrorCode.NOT_POSITIVE);
        }
    }

    public static void ifWholeIsNullThenThrowException(Long... numbers) {
        int length = numbers.length;
        int temp = 0;

        for (Long number : numbers) {
            if (number == null)
                temp++;
        }

        if (length == temp)
            throw new CustomException(ErrorCode.MUST_NON_NULL_AT_LEAST_ONE);
    }

    private static void isValidString(Pattern pattern, String target, ErrorCode errorCode) {
        if (!StringUtils.hasText(target))
            throw new CustomException(ErrorCode.MISSING_REQUIRED);

        if (!pattern.matcher(target).matches())
            throw new CustomException(errorCode);
    }

}
