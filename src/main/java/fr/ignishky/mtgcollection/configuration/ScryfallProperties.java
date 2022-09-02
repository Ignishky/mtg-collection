package fr.ignishky.mtgcollection.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties("scryfall")
public record ScryfallProperties(
        String baseUrl
) {

}
