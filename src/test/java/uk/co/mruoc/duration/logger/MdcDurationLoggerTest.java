package uk.co.mruoc.duration.logger;

import org.junit.jupiter.api.Test;
import uk.co.mruoc.duration.calculator.DurationCalculator;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Collection;

import static uk.co.mruoc.logutil.LogOutputUtils.captureLogLines;

import static org.assertj.core.api.Assertions.assertThat;

class MdcDurationLoggerTest {

    private final Instant NOW = Instant.now();
    private final Clock clock = Clock.fixed(NOW, ZoneId.systemDefault());

    private final MdcDurationLogger logger = MdcDurationLogger.builder()
            .durationCalculator(new DurationCalculator(clock))
            .operationName("my-operation-name")
            .durationName("my-duration-name")
            .build();

    @Test
    void shouldCalculateDifferenceBetweenStartAndCurrentTime() {
        Duration expectedDuration = Duration.ofMinutes(1);
        Instant start = NOW.minus(expectedDuration);

        Collection<String> lines = captureLogLines(() -> logger.log("my-operation", start));

        assertThat(lines).containsExactly("INFO [my-operation:60000::] my-operation took 60000ms");
    }

}
