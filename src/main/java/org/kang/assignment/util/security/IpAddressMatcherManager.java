package org.kang.assignment.util.security;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.web.util.matcher.IpAddressMatcher;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class IpAddressMatcherManager {

    @Getter
    private final List<IpAddressMatcher> ipAddressMatchers;

    public IpAddressMatcherManager(@Value("${allowed.ip}") String allowedIp) {
        String[] allowedIpList = StringUtils.split(allowedIp, ",");
        this.ipAddressMatchers = new ArrayList<>();
        for (String ip : allowedIpList) {
            this.ipAddressMatchers.add(new IpAddressMatcher(ip));
        }
    }

}
