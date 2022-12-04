package org.kang.assignment.service;

import lombok.RequiredArgsConstructor;
import org.kang.assignment.util.security.IpAddressMatcherManager;
import org.springframework.security.web.util.matcher.IpAddressMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class IpAddressAccessControlService {

    private final IpAddressMatcherManager ipAddressMatcherManager;

    public boolean isAccessible(String ip) {
        List<IpAddressMatcher> ipAddressMatchers = ipAddressMatcherManager.getIpAddressMatchers();

        return ipAddressMatchers.stream()
                .anyMatch(matcher -> matcher.matches(ip));
    }

}
