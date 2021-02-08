# Duration Logger

[![Build](https://github.com/michaelruocco/duration-logger/workflows/pipeline/badge.svg)](https://github.com/michaelruocco/duration-logger/actions)
[![codecov](https://codecov.io/gh/michaelruocco/duration-logger/branch/master/graph/badge.svg?token=FWDNP534O7)](https://codecov.io/gh/michaelruocco/duration-logger)
[![Codacy Badge](https://app.codacy.com/project/badge/Grade/272889cf707b4dcb90bf451392530794)](https://www.codacy.com/gh/michaelruocco/duration-logger/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=michaelruocco/duration-logger&amp;utm_campaign=Badge_Grade)
[![BCH compliance](https://bettercodehub.com/edge/badge/michaelruocco/duration-logger?branch=master)](https://bettercodehub.com/)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=michaelruocco_duration-logger&metric=alert_status)](https://sonarcloud.io/dashboard?id=michaelruocco_duration-logger)
[![Technical Debt](https://sonarcloud.io/api/project_badges/measure?project=michaelruocco_duration-logger&metric=sqale_index)](https://sonarcloud.io/dashboard?id=michaelruocco_duration-logger)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=michaelruocco_duration-logger&metric=coverage)](https://sonarcloud.io/dashboard?id=michaelruocco_duration-logger)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=michaelruocco_duration-logger&metric=ncloc)](https://sonarcloud.io/dashboard?id=michaelruocco_duration-logger)
[![Download](https://api.bintray.com/packages/michaelruocco/maven/duration-logger/images/download.svg)](https://bintray.com/michaelruocco/maven/duration-logger/_latestVersion)
[![Maven Central](https://img.shields.io/maven-central/v/com.github.michaelruocco/duration-logger.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22com.github.michaelruocco%22%20AND%20a:%22duration-logger%22)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

## Overview

This small library contains classes that are helpful if you want to log the duration of an operation. This is obviously
very simple code to write, but I found myself doing it in multiple places in various projects. There are tools such as
aspectJ to do this more generically, but I prefer this more specific and lightweight approach.

There are two small bits of functionality provided:

1.   A duration calculator, which will calculate the duration between the current time when the call is made, and a
     start time which is passed into the call.
     
2.   A duration MDC logger, which makes use of the code above to log the duration of an operation, but you can
     additionally specify the name of MDC variables that you want log for the operation name and duration so that the
     values can easily be indexed in your logging for use in things like the ELK stack if required.

### Duration calculator

There are two ways in which the duration calculator can be used.

#### Testable duration calculator

You can create an instance of DurationCalculator and pass it a clock so that you can control how it determines 
the current time when you make your call. e.g.
   
```java
Clock clock = Clock.systemUTC();
DurationCalculator calculator = new DurationCalculator(clock);

...

Instant start = clock.now();
someObject.mySlowOperation();
Duration duration = calculator.durationBetweenNowAnd(start);
// or if you want the duration in millisecond duration
long millis = calculator.millisBetweenNowAnd(start);
```

The advantage of using this approach is that if you create a bean and inject it you can more easily unit test your
results if required.

#### Logging duration calculator
   
You can use the static utility class. This option is often useful if you are not calculating the duration for any
functional reason, but you want to log how long something takes. In this case you might not be too concerned with unit
testing your logging output (which is normally not worth the complexity in my opinion.) This approach lacks the
testability of the example above, but takes fewer lines of code, so can be a more convenient option if you are just
using the duration for logging. e.g.
   
```java
Instant start = clock.now();
someObject.mySlowOperation();
log.info("my slow operation took {}", DurationCalculatorUtils.durationBetweenNowAnd(start));
// or if you want the duration in millisecond duration
log.info("my slow operation took {}ms", DurationCalculatorUtils.millisBetweenNowAnd(start));
```

The above method can also make use of static imports to make it a bit shorter too if you wish.

### MDC duration logger

Following the same pattern as the calculator above, again there are two ways in which the logger can be
used, the first is more easily testable, the second is more convenient if you are just using for logging and are less
concerned about unit testing the duration values calculated. The ```MdcDurationLogger``` class is a generic
one that can be configured to log any operation you wish, my main use case is for logging the duration of mongo
operations, the same pattern could be applied for anything else you want to log.

The mongo logger has two fields which it will log into the SLF4J MDC named mongo-operation, which is the name of
the operation you will pass in your code, and mongo-duration which will be the duration the operation takes. You would
configure these in your logging framework configuration. The unit tests in this repo contain an example using logback,
they use the following pattern from the ```logback-test.xml``` found in ```src/test/resources```

```xml
<appender name="Console" class="ch.qos.logback.core.ConsoleAppender">
    <layout class="ch.qos.logback.classic.PatternLayout">
        <Pattern>
            %p [%X{mongo-operation}:%X{mongo-duration}] %m%n
        </Pattern>
    </layout>
</appender>
```

Then in your code you can have it log the duration by doing the following:

```java
Instant start = Instant.now();
try {
    FindIterable<Document> documents = collection.find(eq("my-id", id.toString()));
    ...
} finally {
    MongoMdcDurationLogger.logDuration("load-my-thing", start);
}
```

Again you can use a static import to shorten the call to logDuration if you wish. The try and finally is
optional, but a good idea if you want to log the duration regardless of whether the operation succeeds or
fails.

## Useful Commands

```gradle
// cleans build directories
// prints currentVersion
// formats code
// builds code
// runs tests
// checks for gradle issues
// checks dependency versions
./gradlew clean currentVersion dependencyUpdates lintGradle spotlessApply build
```