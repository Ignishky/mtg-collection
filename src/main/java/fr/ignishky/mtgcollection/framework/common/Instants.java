package fr.ignishky.mtgcollection.framework.common;

import java.time.Clock;
import java.time.Instant;

public class Instants {

    public static Clock clock = Clock.systemUTC();

    private Instants() {
    }

    public static Instant now() {
        return Instant.now(clock);
    }
}
