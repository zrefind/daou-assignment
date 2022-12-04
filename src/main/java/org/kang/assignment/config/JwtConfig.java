package org.kang.assignment.config;

import lombok.RequiredArgsConstructor;
import org.kang.assignment.common.jwt.JwtFilter;
import org.kang.assignment.common.jwt.JwtUtils;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
public class JwtConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final JwtUtils jwtUtils;

    @Override
    public void configure(HttpSecurity builder) throws Exception {
        JwtFilter jwtFilter = new JwtFilter(jwtUtils);
        builder.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }

}
