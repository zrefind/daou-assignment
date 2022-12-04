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

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

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
        MvcResult mvcResult = mvc.perform(get(findNewbieByPeriodUri)).andReturn();

        Map<String, Long> responseMap = TestUtil.convert(mvcResult, HashMap.class);
        assertTrue(responseMap.containsKey("newbie"));
        assertInstanceOf(Number.class, responseMap.get("newbie"));
    }

}
