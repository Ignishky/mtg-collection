package fr.ignishky.mtgcollection.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.tags.Tag;
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

    private void buildMtgCollectionAPI(OpenAPI openApi, BuildProperties buildProperties) {
        openApi.info(new Info()
                        .title("Mtg-Collection")
                        .version(buildProperties.getVersion()))
                .addTagsItem(new Tag().name("Card Sets").description("All the needed endpoints to manipulate card sets"))
                .servers(null);
    }

}
