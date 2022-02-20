package fr.ignishky.mtgcollection.framework.common;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

public class Instants {

    private static Clock clock = Clock.systemUTC();

    private Instants() {
    }

    public static void freeze() {
        clock = Clock.fixed(now(), ZoneId.of("UTC"));
    }

    public static Instant now() {
        return Instant.now(clock);
    }

    public static void resume() {
        clock = Clock.systemUTC();
    }

}
