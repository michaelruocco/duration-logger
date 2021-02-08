package uk.co.mruoc.duration.logger;

import org.junit.jupiter.api.Test;
import uk.co.mruoc.duration.logger.MdcDurationLogger;
import uk.co.mruoc.duration.logger.MongoMdcDurationLogger;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.co.mruoc.logutil.LogOutputUtils.captureLogLines;

class MongoMdcDurationLoggerTest {

    private final Instant NOW = Instant.now();
    private final Clock clock = Clock.fixed(NOW, ZoneId.systemDefault());

    private final MdcDurationLogger logger = new MongoMdcDurationLogger(clock);

    @Test
    void shouldCalculateDifferenceBetweenStartAndCurrentTime() {
        Duration expectedDuration = Duration.ofSeconds(30);
        Instant start = NOW.minus(expectedDuration);

        Collection<String> lines = captureLogLines(() -> logger.log("my-mongo-write", start));

        assertThat(lines).containsExactly("INFO [::my-mongo-write:30000] my-mongo-write took 30000ms");
    }

}
