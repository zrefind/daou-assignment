package org.kang.assignment.service;

import lombok.RequiredArgsConstructor;
import org.kang.assignment.common.exception.CustomException;
import org.kang.assignment.common.exception.ErrorCode;
import org.kang.assignment.common.jwt.Jwt;
import org.kang.assignment.common.jwt.JwtUtils;
import org.kang.assignment.domain.jwt.RefreshToken;
import org.kang.assignment.domain.jwt.RefreshTokenRepository;
import org.kang.assignment.domain.member.Member;
import org.kang.assignment.domain.member.MemberRepository;
import org.kang.assignment.util.Validator;
import org.kang.assignment.web.dto.auth.AuthRequest;
import org.kang.assignment.web.dto.auth.AuthResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@RequiredArgsConstructor
@Service
public class AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtUtils jwtUtils;

    @Transactional
    public AuthResponse signUp(AuthRequest request) {
        Validator.validateEmail(request.getEmail());
        Validator.validatePassword(request.getPassword());

        if (memberRepository.existsByEmail(request.getEmail()))
            throw new CustomException(ErrorCode.DUPLICATED_EMAIL);

        if (!request.getPassword().equals(request.getPasswordConfirm()))
            throw new CustomException(ErrorCode.DISCREPANT_PASSWORD);

        Member member = request.toMember(passwordEncoder);
        memberRepository.save(member);

        logger.info("success to sign up: {}", member.getEmail());
        return AuthResponse.done(member.getEmail());
    }

    @Transactional
    public AuthResponse signIn(AuthRequest request) {
        Member member = memberRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        UsernamePasswordAuthenticationToken authenticationToken = request.toAuthentication();

        Authentication authentication;
        try {
            authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        } catch (BadCredentialsException e) {
            throw new CustomException(ErrorCode.DISCREPANT_PASSWORD);
        }

        Jwt jwt = jwtUtils.generateJwt(Objects.requireNonNull(authentication));

        RefreshToken refreshToken = RefreshToken.of(authentication.getName(), jwt.getRefreshToken());
        refreshTokenRepository.save(refreshToken);

        logger.info("success to sign in: {}", member.getEmail());
        return AuthResponse.withToken(member, jwt);
    }

}
