package fr.ignishky.mtgcollection.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.tags.Tag;
import io.vavr.collection.List;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {

    @Bean
    public OpenAPI customOpenAPI(BuildProperties buildProperties) {
        return new OpenAPI()
                .tags(List.of(
                        new Tag().name("Card Sets").description("All the needed endpoints to manipulate card sets")
                ).toJavaList())
                .info(new Info()
                        .title("Mtg-Collection")
                        .version(buildProperties.getVersion())
                );
    }
}
