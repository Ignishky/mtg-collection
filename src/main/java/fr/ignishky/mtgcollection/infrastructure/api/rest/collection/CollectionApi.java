package fr.ignishky.mtgcollection.infrastructure.api.rest.collection;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/collection")
public interface CollectionApi {

    @PutMapping(value = "/{cardId}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(OK)
    @Operation(summary = "Add card to the user collection", tags = "Collection")
    ResponseEntity<CardResponse> addCard(@PathVariable String cardId, @RequestBody CollectionRequestBody body);

}
