package fr.ignishky.mtgcollection.common;

import fr.ignishky.mtgcollection.framework.common.Instants;
import org.junit.jupiter.api.extension.*;

import java.time.Clock;
import java.time.ZoneId;

public class InstantFreezeExtension implements BeforeAllCallback, AfterAllCallback {

    @Override
    public void beforeAll(ExtensionContext context) {
        Instants.clock = Clock.fixed(Instants.now(), ZoneId.of("UTC"));
    }

    @Override
    public void afterAll(ExtensionContext context) {
        Instants.clock = Clock.systemUTC();
    }

}
