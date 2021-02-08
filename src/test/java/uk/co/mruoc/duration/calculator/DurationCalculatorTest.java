package uk.co.mruoc.duration.calculator;

import org.junit.jupiter.api.Test;
import uk.co.mruoc.duration.calculator.DurationCalculator;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;

import static org.assertj.core.api.Assertions.assertThat;

class DurationCalculatorTest {

    private final Instant NOW = Instant.now();
    private final Clock clock = Clock.fixed(NOW, ZoneId.systemDefault());

    private final DurationCalculator calculator = new DurationCalculator(clock);

    @Test
    void shouldCalculateDifferenceBetweenStartAndCurrentTime() {
        Duration expectedDuration = Duration.ofMinutes(1);
        Instant start = NOW.minus(expectedDuration);

        Duration duration = calculator.durationBetweenNowAnd(start);

        assertThat(duration).isEqualTo(expectedDuration);
    }

    @Test
    void shouldCalculateDifferenceBetweenStartAndCurrentTimeInMillis() {
        Duration expectedDuration = Duration.ofMinutes(1);
        Instant start = NOW.minus(expectedDuration);

        long millis = calculator.millisBetweenNowAnd(start);

        assertThat(millis).isEqualTo(expectedDuration.toMillis());
    }

}
