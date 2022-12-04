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
    INVALID_EMAIL(HttpStatus.BAD_REQUEST, "invalid email ..."),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "invalid password ..."),

    MISSING_REQUIRED(HttpStatus.BAD_REQUEST, "missing required ..."),

    AUTHORITIES_KEY_IS_NULL(HttpStatus.UNAUTHORIZED, "authorities key is null ..."),
    MALFORMED_JWT(HttpStatus.FORBIDDEN, "malformed jwt ..."),
    UNSUPPORTED_JWT(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "unsupported jwt ..."),
    ILLEGAL_ARGUMENT(HttpStatus.BAD_REQUEST, "illegal argument ..."),
    INVALID_REFRESH_TOKEN(HttpStatus.BAD_REQUEST, "invalid refresh token ..."),
    REFRESH_TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND, "refresh token not found ..."),
    DISCREPANT_REFRESH_TOKEN(HttpStatus.FORBIDDEN, "discrepant refresh token ..."),
    EXPIRED_JWT(HttpStatus.GONE, "expired jwt ..."),

    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "member not found ..."),
    DUPLICATED_EMAIL(HttpStatus.CONFLICT, "duplicated member email ..."),
    DISCREPANT_PASSWORD(HttpStatus.FORBIDDEN, "discrepant password ..."),
    ;

    private final HttpStatus status;
    private final String message;

}