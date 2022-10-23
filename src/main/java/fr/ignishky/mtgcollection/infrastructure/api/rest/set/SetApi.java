package fr.ignishky.mtgcollection.infrastructure.api.rest.set;

import fr.ignishky.mtgcollection.infrastructure.api.rest.set.model.SetResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(SetApi.SETS_PATH)
@Tag(name = "Card Sets", description = "All the needed endpoints to manipulate card sets")
public interface SetApi {

    String SETS_PATH = "/sets";

    @PutMapping
    @Operation(
            summary = "Retrieve the sets cards from Scryfall",
            description = "Calling this endpoint trigger the download of ALL cards and sets available throws the Scryfall API.",
            responses = @ApiResponse(responseCode = "204", description = "All sets and cards have been downloaded."))
    ResponseEntity<Void> loadAll();

    @GetMapping(value = "/{setCode}", produces = APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Return all the cards from a given set",
            description = "The response contain a summary of all the cards in the given set.",
            responses = @ApiResponse(responseCode = "200", description = "The list of cards in the given set."))
    ResponseEntity<SetResponse> getSet(@PathVariable String setCode);

}
