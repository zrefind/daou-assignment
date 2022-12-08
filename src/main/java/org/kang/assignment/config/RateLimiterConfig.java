package org.kang.assignment.config;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class RateLimiterConfig {

    private static final int MAX_BANDWIDTH = 60;
    private static final int MINUTES_TOKEN_REFILL = 1;

    @Bean
    public static Bucket bucket() {
        return Bucket.builder().addLimit(getSimpleBandwidth()).build();
    }

    private static Bandwidth getSimpleBandwidth() {
        return Bandwidth.simple(MAX_BANDWIDTH, Duration.ofMinutes(MINUTES_TOKEN_REFILL));
    }

}
