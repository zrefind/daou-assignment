package org.kang.assignment.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kang.assignment.TestUtil;
import org.kang.assignment.common.exception.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RateLimiterTest {

    private static final int MAX_BANDWIDTH = 30;

    @LocalServerPort
    private int port;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    private final String baseUri = "http://localhost:" + port + "/api/settlement";
    private final String findNewbieByPeriodUri = baseUri + "/search/newbie/2022113001/2022113010";

    @BeforeEach
    public void init() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @WithUserDetails("admin@test.com")
    @Transactional(readOnly = true)
    @DisplayName("속도_제한기_테스트를_위한_시간대별_가입자_수_조회")
    public void findNewbieByPeriodForRateLimiterTest() throws Exception {
        for (int i = 0; i < MAX_BANDWIDTH; i++) {
            mvc.perform(get(findNewbieByPeriodUri))
                    .andExpect(status().isOk());
        }

        mvc.perform(get(findNewbieByPeriodUri))
                .andExpect(result -> TestUtil.expectCustomException(result, ErrorCode.BUCKET_HAS_EXHAUSTED));
    }

}
