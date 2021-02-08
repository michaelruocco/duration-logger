package uk.co.mruoc.duration.logger;

import lombok.extern.slf4j.Slf4j;

import java.time.Clock;
import java.time.Instant;

@Slf4j
public class MongoMdcDurationLoggerUtils {

    private static final DurationLogger LOGGER = new MongoMdcDurationLogger(Clock.systemUTC());

    private MongoMdcDurationLoggerUtils() {
        // utility class
    }

    public static void logDuration(String operation, Instant start) {
        LOGGER.log(operation, start);
    }

}
