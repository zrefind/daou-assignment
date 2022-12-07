package org.kang.assignment.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kang.assignment.TestUtil;
import org.kang.assignment.common.Constants;
import org.kang.assignment.common.enums.ResponseType;
import org.kang.assignment.common.exception.ErrorCode;
import org.kang.assignment.domain.jwt.RefreshToken;
import org.kang.assignment.domain.jwt.RefreshTokenRepository;
import org.kang.assignment.domain.member.Member;
import org.kang.assignment.domain.member.MemberRepository;
import org.kang.assignment.web.dto.auth.AuthRequest;
import org.kang.assignment.web.dto.auth.AuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.Cookie;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthControllerTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    private static final String BASE_URI = "/api/auth";
    private static final String SIGN_UP_URI = BASE_URI + "/sign-up";
    private static final String SIGN_IN_URI = BASE_URI + "/sign-in";
    private static final String EMAIL = "test@test.com";
    private static final String PASSWORD = "1q2w3e4r!";
    private static final String PASSWORD_CONFIRM = "1q2w3e4r!";

    private MockMvc mvc;

    @BeforeEach
    public void init() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @Transactional
    @DisplayName("회원가입")
    public void signUp() throws Exception {
        AuthRequest request = AuthRequest.builder()
                .email(EMAIL)
                .password(PASSWORD)
                .passwordConfirm(PASSWORD_CONFIRM)
                .build();

        MvcResult mvcResult = mvc.perform(post(SIGN_UP_URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andReturn();

        AuthResponse response = TestUtil.convert(mvcResult, AuthResponse.class);
        assertEquals(ResponseType.DONE, response.getResponseType());
        assertEquals(EMAIL, response.getEmail());

        List<Member> members = memberRepository.findAll();
        Member member = members.get(members.size() - 1);
        assertEquals(EMAIL, member.getEmail());
    }

    @Test
    @Transactional
    @DisplayName("회원가입__이메일_중복")
    public void signUp__duplicated_email() throws Exception {
        AuthRequest request = AuthRequest.builder()
                .email("admin@test.com")
                .password(PASSWORD)
                .passwordConfirm(PASSWORD_CONFIRM)
                .build();

        mvc.perform(post(SIGN_UP_URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(result -> TestUtil.expectCustomException(result, ErrorCode.DUPLICATED_EMAIL));
    }

    @Test
    @Transactional
    @DisplayName("회원가입__패스워드_불일치")
    public void signUp__discrepant_password() throws Exception {
        AuthRequest request = AuthRequest.builder()
                .email(EMAIL)
                .password(PASSWORD)
                .passwordConfirm("0123456789!")
                .build();

        mvc.perform(post(SIGN_UP_URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(result -> TestUtil.expectCustomException(result, ErrorCode.DISCREPANT_PASSWORD));
    }

    @Test
    @Transactional
    @DisplayName("로그인")
    public void signIn() throws Exception {
        AuthRequest request = AuthRequest.builder()
                .email("admin@test.com")
                .password(PASSWORD)
                .build();

        MvcResult mvcResult = mvc.perform(post(SIGN_IN_URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andReturn();

        AuthResponse response = TestUtil.convert(mvcResult, AuthResponse.class);
        assertEquals(ResponseType.DONE, response.getResponseType());
        assertEquals("admin@test.com", response.getEmail());

        MockHttpServletResponse httpServletResponse = mvcResult.getResponse();
        Cookie[] jwtCookies = httpServletResponse.getCookies();
        assert jwtCookies.length == 2;
        Cookie accessTokenCookie = jwtCookies[0];
        Cookie refreshTokenCookie = jwtCookies[1];

        assertEquals(Constants.ACCESS_TOKEN_NAME, accessTokenCookie.getName());
        assertEquals(Constants.REFRESH_TOKEN_NAME, refreshTokenCookie.getName());
        assertEquals(Constants.JWT_COOKIE_PATH, accessTokenCookie.getPath());
        assertEquals(Constants.JWT_COOKIE_PATH, refreshTokenCookie.getPath());
        assertTrue(accessTokenCookie.isHttpOnly());
        assertTrue(refreshTokenCookie.isHttpOnly());

        List<RefreshToken> refreshTokens = refreshTokenRepository.findAll();
        RefreshToken refreshToken = refreshTokens.get(refreshTokens.size() - 1);
        assertEquals(refreshTokenCookie.getValue(), refreshToken.getValue());
    }

    @Test
    @Transactional
    @DisplayName("로그인__없는_회원")
    public void signIn__member_not_found() throws Exception {
        AuthRequest request = AuthRequest.builder()
                .email("unknown@test.com")
                .password(PASSWORD)
                .build();

        mvc.perform(post(SIGN_IN_URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(result -> TestUtil.expectCustomException(result, ErrorCode.MEMBER_NOT_FOUND));
    }

    @Test
    @Transactional
    @DisplayName("로그인__패스워드_불일치")
    public void signIn__discrepant_password() throws Exception {
        AuthRequest request = AuthRequest.builder()
                .email("admin@test.com")
                .password("0123456789!")
                .build();

        mvc.perform(post(SIGN_IN_URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(result -> TestUtil.expectCustomException(result, ErrorCode.DISCREPANT_PASSWORD));
    }

}
