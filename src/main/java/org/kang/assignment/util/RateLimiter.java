package org.kang.assignment.util;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;

import java.time.Duration;

public class RateLimiter {

    private static final int MAX_BANDWIDTH = 60;
    private static final int MINUTES_TOKEN_REFILL = 1;
    private static final Bucket BUCKET = Bucket.builder().addLimit(getSimpleBandwidth()).build();

    public static Bucket getBucket() {
        return BUCKET;
    }

    private static Bandwidth getSimpleBandwidth() {
        return Bandwidth.simple(MAX_BANDWIDTH, Duration.ofMinutes(MINUTES_TOKEN_REFILL));
    }

}
