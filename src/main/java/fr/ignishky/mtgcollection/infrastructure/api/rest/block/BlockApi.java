package fr.ignishky.mtgcollection.infrastructure.api.rest.block;

import fr.ignishky.mtgcollection.infrastructure.api.rest.block.model.BlocksResponse;
import fr.ignishky.mtgcollection.infrastructure.api.rest.set.model.SetsResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/blocks")
@Tag(name = "Blocks", description = "All the needed endpoints to manage blocks")
public interface BlockApi {

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Return all the MTG blocks",
            description = "The response contain all the blocks sorting by publishing date.",
            responses = @ApiResponse(responseCode = "200", description = "The list of all card blocks."))
    ResponseEntity<BlocksResponse> getAll();

    @GetMapping(value = "/{blockCode}", produces = APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Return all the sets from a given block",
            description = "The response contain all the sets in the given block.",
            responses = @ApiResponse(responseCode = "200", description = "The list of sets in the given block."))
    ResponseEntity<SetsResponse> getSets(@PathVariable String blockCode);

}
