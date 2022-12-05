package org.kang.assignment.web.controller;

import io.github.bucket4j.Bucket;
import lombok.RequiredArgsConstructor;
import org.kang.assignment.common.Constants;
import org.kang.assignment.common.exception.CustomException;
import org.kang.assignment.common.exception.ErrorCode;
import org.kang.assignment.common.jwt.Jwt;
import org.kang.assignment.service.AuthService;
import org.kang.assignment.util.RateLimiter;
import org.kang.assignment.web.dto.auth.AuthRequest;
import org.kang.assignment.web.dto.auth.AuthResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final Bucket bucket = RateLimiter.generateSimpleBucket();
    private final AuthService authService;

    @PostMapping("/sign-up")
    public ResponseEntity<AuthResponse> signUp(@RequestBody AuthRequest request) {
        if (bucket.tryConsume(1)) {
            logger.debug("bucket remains: {}", bucket.getAvailableTokens());
            return ResponseEntity.ok(authService.signUp(request));
        }

        throw new CustomException(ErrorCode.BUCKET_HAS_EXHAUSTED);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<AuthResponse> signIn(@RequestBody AuthRequest request, HttpServletResponse response) {
        if (bucket.tryConsume(1)) {
            logger.debug("bucket remains: {}", bucket.getAvailableTokens());

            AuthResponse authResponse = authService.signIn(request);
            responseJwtCookies(authResponse.getJwt(), response);

            return ResponseEntity.ok(AuthResponse.done(authResponse.getEmail()));
        }

        throw new CustomException(ErrorCode.BUCKET_HAS_EXHAUSTED);
    }

    private static void responseJwtCookies(Jwt jwt, HttpServletResponse response) {
        Cookie[] jwtCookies = new Cookie[2];
        jwtCookies[0] = new Cookie(Constants.ACCESS_TOKEN_NAME, jwt.getAccessToken());
        jwtCookies[1] = new Cookie(Constants.REFRESH_TOKEN_NAME, jwt.getRefreshToken());

        for (Cookie cookie : jwtCookies) {
            cookie.setPath(Constants.JWT_COOKIE_PATH);
            cookie.setHttpOnly(true);
            response.addCookie(cookie);
        }
    }

}
