package org.kang.assignment.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kang.assignment.TestUtil;
import org.kang.assignment.common.enums.ResponseType;
import org.kang.assignment.common.exception.ErrorCode;
import org.kang.assignment.domain.settlement.SettlementRepository;
import org.kang.assignment.web.dto.settlement.SettlementRequest;
import org.kang.assignment.web.dto.settlement.SettlementResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SettlementControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private SettlementRepository settlementRepository;

    private MockMvc mvc;

    private final String baseUri = "http://localhost:" + port + "/api/settlement";
    private final String findNewbieByPeriodUri = baseUri + "/search/newbie/2022113001/2022113010";
    private final String findBolterByPeriodUri = baseUri + "/search/bolter/2022113001/2022113010";
    private final String findPaymentByPeriodUri = baseUri + "/search/payment/2022113001/2022113010";
    private final String findUsedByPeriodUri = baseUri + "/search/used/2022113001/2022113010";
    private final String findSalesByPeriodUri = baseUri + "/search/sales/2022113001/2022113010";
    private final String enrollmentUri = baseUri + "/enrollment";

    private static final String TIME = "2022010100";

    @BeforeEach
    public void init() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @WithUserDetails("admin@test.com")
    @Transactional(readOnly = true)
    @DisplayName("시간대별_가입자_수_조회")
    public void findNewbieByPeriod() throws Exception {
        MvcResult mvcResult = mvc.perform(get(findNewbieByPeriodUri))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        Long response = TestUtil.convert(mvcResult, Long.class);
        assertTrue(response > -1L);
    }

    @Test
    @WithUserDetails("admin@test.com")
    @Transactional(readOnly = true)
    @DisplayName("시간대별_탈퇴자_수_조회")
    public void findBolterByPeriod() throws Exception {
        MvcResult mvcResult = mvc.perform(get(findBolterByPeriodUri))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        Long response = TestUtil.convert(mvcResult, Long.class);
        assertTrue(response > -1L);
    }

    @Test
    @WithUserDetails("admin@test.com")
    @Transactional(readOnly = true)
    @DisplayName("시간대별_결제_금액_조회")
    public void findPaymentByPeriod() throws Exception {
        MvcResult mvcResult = mvc.perform(get(findPaymentByPeriodUri))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        Long response = TestUtil.convert(mvcResult, Long.class);
        assertTrue(response > -1L);
    }

    @Test
    @WithUserDetails("admin@test.com")
    @Transactional(readOnly = true)
    @DisplayName("시간대별_사용_금액_조회")
    public void findUsedByPeriod() throws Exception {
        MvcResult mvcResult = mvc.perform(get(findUsedByPeriodUri))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        Long response = TestUtil.convert(mvcResult, Long.class);
        assertTrue(response > -1L);
    }

    @Test
    @WithUserDetails("admin@test.com")
    @Transactional(readOnly = true)
    @DisplayName("시간대별_매출_금액_조회")
    public void findSalesByPeriod() throws Exception {
        MvcResult mvcResult = mvc.perform(get(findSalesByPeriodUri))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        Long response = TestUtil.convert(mvcResult, Long.class);
        assertTrue(response > -1L);
    }

    @Test
    @WithUserDetails("admin@test.com")
    @Transactional
    @DisplayName("시간대별_각_항목_입력")
    public void enrollment() throws Exception {
        SettlementRequest request = SettlementRequest.builder()
                .time(TIME)
                .newbie(10L)
                .payment(22000L)
                .sales(123000L)
                .build();

        MvcResult mvcResult = mvc.perform(post(enrollmentUri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andReturn();

        SettlementResponse response = TestUtil.convert(mvcResult, SettlementResponse.class);
        assertEquals(ResponseType.DONE, response.getResponseType());
        assertEquals(TIME, response.getTime().replace("-", "").replace(" ", ""));
        assertEquals(10L, response.getNewbie());
        assertNull(response.getBolter());
        assertEquals(22000L, response.getPayment());
        assertNull(response.getUsed());
        assertEquals(123000L, response.getSales());
    }

    @Test
    @WithUserDetails("admin@test.com")
    @Transactional
    @DisplayName("시간대별_각_항목_입력__시간대_중복")
    public void enrollment__duplicated() throws Exception {
        SettlementRequest request = SettlementRequest.builder()
                .time("2022113000")
                .newbie(10L)
                .payment(22000L)
                .sales(123000L)
                .build();

        mvc.perform(post(enrollmentUri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(result -> TestUtil.expectCustomException(result, ErrorCode.DUPLICATED_SETTLEMENT));
    }

}
