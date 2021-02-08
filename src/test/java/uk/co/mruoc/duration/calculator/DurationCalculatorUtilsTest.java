package uk.co.mruoc.duration.calculator;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

class DurationCalculatorUtilsTest {

    private static final Duration ALLOWED_DIFFERENCE = Duration.ofMillis(500);

    @Test
    void shouldCalculateDifferenceBetweenStartAndCurrentTime() {
        Duration expectedDuration = Duration.ofMinutes(1);
        Instant now = Instant.now();
        Instant start = now.minus(expectedDuration);

        Duration duration = DurationCalculatorUtils.durationBetweenNowAnd(start);

        assertThat(duration).isCloseTo(expectedDuration, ALLOWED_DIFFERENCE);
    }

    @Test
    void shouldCalculateDifferenceBetweenStartAndCurrentTimeInMilllis() {
        Duration expectedDuration = Duration.ofMinutes(1);
        Instant now = Instant.now();
        Instant start = now.minus(expectedDuration);

        long millis = DurationCalculatorUtils.millisBetweenNowAnd(start);

        assertThat(millis).isCloseTo(expectedDuration.toMillis(), within(ALLOWED_DIFFERENCE.toMillis()));
    }

}
