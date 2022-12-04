package org.kang.assignment;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.kang.assignment.common.exception.CustomException;
import org.kang.assignment.common.exception.ErrorCode;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestUtil {

    public static <T> T convert(MvcResult mvcResult, Class<T> clazz) throws Exception {
        return new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8), clazz);
    }

    public static void expectCustomException(MvcResult mvcResult, ErrorCode errorCode) {
        assertTrue(mvcResult.getResolvedException() instanceof CustomException);

        CustomException customException = (CustomException) mvcResult.getResolvedException();
        assert customException != null;

        assertEquals(errorCode, customException.getErrorCode());
    }

}
