package fr.ignishky.mtgcollection.configuration;

import com.fasterxml.jackson.databind.JavaType;
import io.swagger.v3.core.converter.AnnotatedType;
import io.swagger.v3.core.converter.ModelConverter;
import io.swagger.v3.core.converter.ModelConverterContext;
import io.swagger.v3.core.util.Json;
import io.swagger.v3.oas.models.media.ArraySchema;
import io.swagger.v3.oas.models.media.Schema;
import io.vavr.collection.Seq;
import org.springframework.stereotype.Component;

import java.util.Iterator;

@Component
public class VavrSeqSupportConverter implements ModelConverter {

    @Override
    public Schema<?> resolve(AnnotatedType type, ModelConverterContext context, Iterator<ModelConverter> chain) {
        JavaType javaType = Json.mapper().constructType(type.getType());
        if (javaType != null && Seq.class.isAssignableFrom(javaType.getRawClass())) {
            AnnotatedType annotatedType = type
                    .type(javaType.containedType(0))
                    .skipOverride(true);
            return new ArraySchema().items(resolve(annotatedType, context, chain));
        }
        return chain.hasNext() ? chain.next().resolve(type, context, chain) : null;
    }
}
