package org.kang.assignment.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    BUCKET_HAS_EXHAUSTED(HttpStatus.TOO_MANY_REQUESTS, "bucket has exhausted ..."),
    MISSING_REQUIRED(HttpStatus.BAD_REQUEST, "missing required ..."),
    CAN_NOT_READ_FILE(HttpStatus.BAD_REQUEST, "can not read file ..."),

    INVALID_FACTOR(HttpStatus.BAD_REQUEST, "invalid factor ..."),
    INVALID_PERIOD(HttpStatus.BAD_REQUEST, "invalid period ..."),
    INVALID_TIME(HttpStatus.BAD_REQUEST, "invalid time ..."),
    INVALID_EMAIL(HttpStatus.BAD_REQUEST, "invalid email ..."),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "invalid password ..."),
    INVALID_FILE(HttpStatus.BAD_REQUEST, "invalid file ..."),
    NOT_POSITIVE(HttpStatus.BAD_REQUEST, "not positive number ..."),

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

    SETTLEMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "settlement not found ..."),
    DUPLICATED_SETTLEMENT(HttpStatus.CONFLICT, "duplicated settlement ..."),
    ;

    private final HttpStatus status;
    private final String message;

}
