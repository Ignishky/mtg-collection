package fr.ignishky.mtgcollection.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {

    @Bean
    public GroupedOpenApi mtgApi(BuildProperties buildProperties) {
        return GroupedOpenApi.builder()
                .group("mtg-collection")
                .packagesToScan("fr.ignishky.mtgcollection.infrastructure.api.rest")
                .addOpenApiCustomiser(openApi -> buildMtgCollectionAPI(openApi, buildProperties))
                .build();
    }

    private static void buildMtgCollectionAPI(OpenAPI openApi, BuildProperties buildProperties) {
        openApi.info(new Info()
                        .title("Mtg-Collection")
                        .description("An application to manage your collection of MTG cards.")
                        .license(new License().name("Apache V2.0"))
                        .version(buildProperties.getVersion()));
    }

}
