package org.kang.assignment.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    TEMP(HttpStatus.NOT_FOUND, "not found"),

    INVALID_FACTOR(HttpStatus.BAD_REQUEST, "invalid factor ..."),
    INVALID_PERIOD(HttpStatus.BAD_REQUEST, "invalid period ..."),

    MISSING_REQUIRED(HttpStatus.BAD_REQUEST, "missing required ..."),
    ;

    private final HttpStatus status;
    private final String message;

}
