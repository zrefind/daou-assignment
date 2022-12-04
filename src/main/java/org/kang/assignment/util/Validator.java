package org.kang.assignment.util;

import org.kang.assignment.common.exception.CustomException;
import org.kang.assignment.common.exception.ErrorCode;
import org.springframework.util.StringUtils;

import java.util.regex.Pattern;

public class Validator {

    private static final String REGEX_PERIOD = "(19|20)\\d{2}(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[01])(0[0-9]|1[0-9]|2[0-3])";
    private static final Pattern PATTERN_PERIOD = Pattern.compile(REGEX_PERIOD);

    public static void validatePeriod(String from, String to) {
        isValidString(PATTERN_PERIOD, from, ErrorCode.INVALID_PERIOD);
        isValidString(PATTERN_PERIOD, to, ErrorCode.INVALID_PERIOD);

        if (Integer.parseInt(from) > Integer.parseInt(to))
            throw new CustomException(ErrorCode.INVALID_PERIOD);
    }

    private static void isValidString(Pattern pattern, String target, ErrorCode errorCode) {
        if (!StringUtils.hasText(target))
            throw new CustomException(ErrorCode.MISSING_REQUIRED);

        if (!pattern.matcher(target).matches())
            throw new CustomException(errorCode);
    }

}
