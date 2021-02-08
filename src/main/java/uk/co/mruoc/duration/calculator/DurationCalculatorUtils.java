package uk.co.mruoc.duration.calculator;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;

public class DurationCalculatorUtils {

    private static final DurationCalculator CALCULATOR = new DurationCalculator(Clock.systemUTC());

    private DurationCalculatorUtils() {
        // utility class
    }

    public static long millisBetweenNowAnd(Instant start) {
        return CALCULATOR.millisBetweenNowAnd(start);
    }

    public static Duration durationBetweenNowAnd(Instant start) {
        return CALCULATOR.durationBetweenNowAnd(start);
    }

}
