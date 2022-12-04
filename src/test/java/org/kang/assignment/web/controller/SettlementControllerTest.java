package org.kang.assignment.web.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kang.assignment.TestUtil;
import org.kang.assignment.domain.settlement.SettlementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    private final String findNewbieByPeriodUri = baseUri + "/newbie/2022113001/2022113010";
    private final String findBolterByPeriodUri = baseUri + "/bolter/2022113001/2022113010";
    private final String findPaymentByPeriodUri = baseUri + "/payment/2022113001/2022113010";
    private final String findUsedByPeriodUri = baseUri + "/used/2022113001/2022113010";
    private final String findSalesByPeriodUri = baseUri + "/sales/2022113001/2022113010";

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

}
