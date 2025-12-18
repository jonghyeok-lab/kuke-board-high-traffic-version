package kuke.board.hotarticle.utils;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class TimeCalculatorUtilsTest {

    @Test
    void calculateOffset() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime midnight = now.plusDays(1).with(LocalTime.MIDNIGHT);

        Duration between = Duration.between(now, midnight);
        Duration between1 = Duration.between(midnight, now);
        System.out.println("between = " + between);
        System.out.println("between = " + between1);
    }
}