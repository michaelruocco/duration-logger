package uk.co.mruoc.duration.logger;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import uk.co.mruoc.duration.calculator.DurationCalculator;

import java.time.Instant;

@Slf4j
@Builder
public class MdcDurationLogger implements DurationLogger {

    private final DurationCalculator durationCalculator;
    private final String operationName;
    private final String durationName;

    @Override
    public void log(String operation, Instant start) {
        long duration = durationCalculator.millisBetweenNowAnd(start);
        MDC.put(operationName, operation);
        MDC.put(durationName, Long.toString(duration));
        log.info("{} took {}ms", operation, duration);
        MDC.remove(operationName);
        MDC.remove(durationName);
    }

}
