package uk.co.mruoc.duration.logger;

import lombok.extern.slf4j.Slf4j;
import uk.co.mruoc.duration.calculator.DurationCalculator;

import java.time.Clock;

@Slf4j
public class MongoMdcDurationLogger extends MdcDurationLogger {

    public MongoMdcDurationLogger(Clock clock) {
        this(new DurationCalculator(clock));
    }

    public MongoMdcDurationLogger(DurationCalculator durationCalculator) {
        super(durationCalculator, "mongo-operation", "mongo-duration");
    }

}
