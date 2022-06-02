package fr.ignishky.mtgcollection.fixtures;

import fr.ignishky.mtgcollection.framework.common.Instants;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class InstantFreezeExtension implements BeforeAllCallback, AfterAllCallback {

    @Override
    public void beforeAll(ExtensionContext context) {
        Instants.freeze();
    }

    @Override
    public void afterAll(ExtensionContext context) {
        Instants.resume();
    }

}
