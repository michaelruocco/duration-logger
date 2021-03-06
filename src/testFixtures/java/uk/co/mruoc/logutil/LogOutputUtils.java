package uk.co.mruoc.logutil;

import uk.org.webcompere.systemstubs.ThrowingRunnable;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import static uk.org.webcompere.systemstubs.SystemStubs.tapSystemOut;

public class LogOutputUtils {

    private LogOutputUtils() {
        // utility class
    }

    public static Collection<String> captureLogLines(ThrowingRunnable statement) {
        try {
            String[] logLines = tapSystemOut(statement).split(System.lineSeparator());
            return Arrays.stream(logLines).collect(Collectors.toList());
        } catch (Exception e) {
            throw new LogOutputException(e);
        }
    }

}
