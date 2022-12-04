package org.kang.assignment.util.security;

import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.Nullable;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

public class SecurityUtils {

    private static final String[] IP_HEADER_CANDIDATES = {
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP_X_FORWARDED_FOR",
            "HTTP_X_FORWARDED",
            "HTTP_X_CLUSTER_CLIENT_IP",
            "HTTP_CLIENT_IP",
            "HTTP_FORWARDED_FOR",
            "HTTP_FORWARDED",
            "HTTP_VIA",
            "REMOTE_ADDR"
    };

    public static String getClientIp(HttpServletRequest request) {
        String ip = getIpXff(request);

        for (String ipHeader : IP_HEADER_CANDIDATES) {
            ip = request.getHeader(ipHeader);
            if (!isInvalidIp(ip))
                return ip;
        }

        return ip != null ? ip : request.getRemoteAddr();
    }

    @Nullable
    private static String getIpXff(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");

        if (isMultipleIpXff(ip))
            ip = getClientIpWhenMultipleIpXff(ip);

        return ip;
    }

    private static boolean isMultipleIpXff(String ip) {
        return StringUtils.contains(ip, ",");
    }

    private static String getClientIpWhenMultipleIpXff(String ipList) {
        return StringUtils.split(ipList, ",")[0];
    }

    private static boolean isInvalidIp(String ip) {
        return ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip);
    }

}
