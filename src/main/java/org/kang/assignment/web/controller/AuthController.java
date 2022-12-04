package org.kang.assignment.web.controller;

import lombok.RequiredArgsConstructor;
import org.kang.assignment.common.Constants;
import org.kang.assignment.common.jwt.Jwt;
import org.kang.assignment.service.AuthService;
import org.kang.assignment.web.dto.auth.AuthRequest;
import org.kang.assignment.web.dto.auth.AuthResponse;
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

    private final AuthService authService;

    @PostMapping("/sign-up")
    public ResponseEntity<AuthResponse> signUp(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.signUp(request));
    }

    @PostMapping("/sign-in")
    public ResponseEntity<AuthResponse> signIn(@RequestBody AuthRequest request, HttpServletResponse response) {
        AuthResponse authResponse = authService.signIn(request);
        responseJwtCookies(authResponse.getJwt(), response);

        return ResponseEntity.ok(AuthResponse.done(authResponse.getEmail()));
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
