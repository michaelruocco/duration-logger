package uk.co.mruoc.duration.logger;

import org.apache.commons.collections4.IterableUtils;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.co.mruoc.duration.logger.MongoMdcDurationLoggerUtils.logDuration;
import static uk.co.mruoc.logutil.LogOutputUtils.captureLogLines;

class MongoMdcDurationLoggerUtilsTest {

    @Test
    void shouldCalculateDifferenceBetweenStartAndCurrentTime() {
        Instant now = Instant.now();
        Duration expectedDuration = Duration.ofSeconds(30);
        Instant start = now.minus(expectedDuration);

        Collection<String> lines = captureLogLines(() -> logDuration("my-mongo-write", start));

        assertThat(lines).hasSize(1);
        String lastLine = IterableUtils.get(lines, lines.size() - 1);
        assertThat(lastLine).matches("INFO \\[::my-mongo-write:[0-9]*] my-mongo-write took [0-9]*ms");
    }

}
