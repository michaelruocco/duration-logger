package uk.co.mruoc.duration.calculator;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;

public class DurationCalculator {

    private final Clock clock;

    public DurationCalculator(Clock clock) {
        this.clock = clock;
    }

    public long millisBetweenNowAnd(Instant start) {
        return durationBetweenNowAnd(start).toMillis();
    }

    public Duration durationBetweenNowAnd(Instant start) {
        return Duration.between(start, clock.instant());
    }

}
