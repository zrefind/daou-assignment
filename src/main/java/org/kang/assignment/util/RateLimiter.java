package org.kang.assignment.util;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;

import java.time.Duration;

public class RateLimiter {

    private static final int MAX_BANDWIDTH = 30;
    private static final int MINUTES_TOKEN_REFILL = 1;

    public static Bucket generateSimpleBucket() {
        return Bucket.builder()
                .addLimit(getSimpleBandwidth())
                .build();
    }

    private static Bandwidth getSimpleBandwidth() {
        return Bandwidth.simple(MAX_BANDWIDTH, Duration.ofMinutes(MINUTES_TOKEN_REFILL));
    }

}
