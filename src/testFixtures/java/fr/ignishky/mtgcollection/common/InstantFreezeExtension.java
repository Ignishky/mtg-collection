package fr.ignishky.mtgcollection.common;

import fr.ignishky.mtgcollection.framework.common.Instants;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.time.Clock;
import java.time.ZoneId;

public class InstantFreezeExtension implements BeforeEachCallback, AfterEachCallback {

    @Override
    public void beforeEach(ExtensionContext context) {
        Instants.clock = Clock.fixed(Instants.now(), ZoneId.of("UTC"));
    }

    @Override
    public void afterEach(ExtensionContext context) {
        Instants.clock = Clock.systemUTC();
    }

}
