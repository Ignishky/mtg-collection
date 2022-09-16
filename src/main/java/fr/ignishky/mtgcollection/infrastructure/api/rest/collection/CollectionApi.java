package fr.ignishky.mtgcollection.infrastructure.api.rest.collection;

import fr.ignishky.mtgcollection.infrastructure.api.rest.collection.model.CardResponse;
import fr.ignishky.mtgcollection.infrastructure.api.rest.collection.model.CollectionRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.vavr.collection.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static fr.ignishky.mtgcollection.infrastructure.api.rest.collection.CollectionApi.COLLECTION_PATH;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(COLLECTION_PATH)
@Tag(name = "Collection", description = "All the needed endpoints to manipulate user cards collection")
public interface CollectionApi {

    String COLLECTION_PATH = "/collection";

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @Operation(summary = "Retrieve the user collection")
    ResponseEntity<List<CardResponse>> getCollection();

    @PutMapping(value = "/{cardId}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @Operation(summary = "Add card to the user collection")
    ResponseEntity<CardResponse> addCard(@PathVariable UUID cardId, @RequestBody CollectionRequest request);

    @DeleteMapping(value = "/{cardId}", produces = APPLICATION_JSON_VALUE)
    @Operation(summary = "Remove card from the user collection")
    ResponseEntity<CardResponse> deleteCard(@PathVariable UUID cardId);

}
