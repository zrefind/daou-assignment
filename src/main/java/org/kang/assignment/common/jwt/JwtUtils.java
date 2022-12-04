package org.kang.assignment.common.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.kang.assignment.common.exception.CustomException;
import org.kang.assignment.common.exception.ErrorCode;
import org.kang.assignment.domain.jwt.RefreshToken;
import org.kang.assignment.domain.jwt.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtUtils {

    private static final String AUTHORITIES_KEY = "auth";
    private static final String BEARER_TYPE = "bearer";
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30;
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7;

    private final Key key;
    private final RefreshTokenRepository refreshTokenRepository;

    public JwtUtils(@Value("${jwt.secret}") String secretKey, RefreshTokenRepository refreshTokenRepository) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public Jwt generateJwt(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();
        Date accessTokenExpiresIn = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
        Date refreshTokenExpiresIn = new Date(now + REFRESH_TOKEN_EXPIRE_TIME);

        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .setExpiration(accessTokenExpiresIn)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
        String refreshToken = Jwts.builder()
                .setExpiration(refreshTokenExpiresIn)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();

        return Jwt.of(BEARER_TYPE, accessToken, refreshToken, accessTokenExpiresIn.getTime());
    }

    public Authentication getAuthentication(String accessToken) {
        Claims claims = parseClaims(accessToken);
        if (claims.get(AUTHORITIES_KEY) == null)
            throw new CustomException(ErrorCode.AUTHORITIES_KEY_IS_NULL);

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        UserDetails principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    public Jwt validateToken(String[] jwt) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(jwt[0]);
            return null;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            throw new CustomException(ErrorCode.MALFORMED_JWT);
        } catch (ExpiredJwtException e) {
            return reissue(jwt);
        } catch (UnsupportedJwtException e) {
            throw new CustomException(ErrorCode.UNSUPPORTED_JWT);
        } catch (IllegalArgumentException e) {
            throw new CustomException(ErrorCode.ILLEGAL_ARGUMENT);
        }
    }

    private Claims parseClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    private Jwt reissue(String[] jwt) {
        try {
            if (validateToken(new String[] { jwt[1] }) != null)
                throw new CustomException(ErrorCode.INVALID_REFRESH_TOKEN);

            Authentication authentication = getAuthentication(jwt[0]);
            RefreshToken refreshToken = refreshTokenRepository.findByKey(authentication.getName())
                    .orElseThrow(() -> new CustomException(ErrorCode.REFRESH_TOKEN_NOT_FOUND));

            if (!refreshToken.getValue().equals(jwt[1]))
                throw new CustomException(ErrorCode.DISCREPANT_REFRESH_TOKEN);

            return generateJwt(authentication);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new CustomException(ErrorCode.EXPIRED_JWT);
        }
    }

}
