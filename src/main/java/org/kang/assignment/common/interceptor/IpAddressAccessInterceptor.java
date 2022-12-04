package org.kang.assignment.common.interceptor;

import lombok.RequiredArgsConstructor;
import org.kang.assignment.service.IpAddressAccessControlService;
import org.kang.assignment.util.security.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@Component
public class IpAddressAccessInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(IpAddressAccessInterceptor.class);

    private final IpAddressAccessControlService ipAddressAccessControlService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String clientIpAddress = SecurityUtils.getClientIp(request);
        if (!ipAddressAccessControlService.isAccessible(clientIpAddress)) {
            logger.warn("FORBIDDEN ACCESS: from not allowed ip - {}", clientIpAddress);
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return false;
        }

        return true;
    }

}
