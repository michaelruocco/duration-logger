package uk.co.mruoc.duration.logger;

import java.time.Instant;

public interface DurationLogger {

    void log(String operation, Instant start);

}
