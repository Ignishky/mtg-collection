package fr.ignishky.mtgcollection.infrastructure.api.rest.collection;

import io.swagger.v3.oas.annotations.Operation;
import io.vavr.collection.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/collection")
public interface CollectionApi {

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @Operation(summary = "Retrieve the user collection", tags = "Collection")
    ResponseEntity<List<CardResponse>> getCollection();

    @PutMapping(value = "/{cardId}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @Operation(summary = "Add card to the user collection", tags = "Collection")
    ResponseEntity<CardResponse> addCard(@PathVariable String cardId, @RequestBody CollectionRequestBody body);

}
