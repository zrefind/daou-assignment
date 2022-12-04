package org.kang.assignment.common.jwt;

import lombok.RequiredArgsConstructor;
import org.kang.assignment.common.Constants;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String[] resolvedJwt = resolveJwt(request);
        String accessToken = resolvedJwt[0];

        if (StringUtils.hasText(accessToken)) {
            Jwt jwt = jwtUtils.validateToken(resolvedJwt);
            String token = accessToken;

            if (jwt != null) {
                token = jwt.getAccessToken();
                reissueJwtCookies(jwt.getAccessToken(), resolvedJwt[1], response);
            }

            Authentication authentication = jwtUtils.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    private String[] resolveJwt(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String[] jwt = new String[2];

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(Constants.ACCESS_TOKEN_NAME))
                    jwt[0] = cookie.getValue();
                if (cookie.getName().equals(Constants.REFRESH_TOKEN_NAME))
                    jwt[1] = cookie.getValue();
            }
        }

        return jwt;
    }

    private void reissueJwtCookies(String accessToken, String refreshToken, HttpServletResponse response) {
        Cookie[] jwtCookies = new Cookie[2];
        jwtCookies[0] = new Cookie(Constants.ACCESS_TOKEN_NAME, accessToken);
        jwtCookies[1] = new Cookie(Constants.REFRESH_TOKEN_NAME, refreshToken);

        for (Cookie cookie : jwtCookies) {
            cookie.setPath(Constants.JWT_COOKIE_PATH);
            cookie.setHttpOnly(true);
            response.addCookie(cookie);
        }
    }

}
