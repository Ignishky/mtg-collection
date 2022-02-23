package fr.ignishky.mtgcollection.infrastructure.api.rest.set;

import io.swagger.v3.oas.annotations.Operation;
import io.vavr.collection.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/sets")
public interface SetApi {

    @PutMapping
    @ResponseStatus(NO_CONTENT)
    @Operation(summary = "Retrieve the sets cards from Scryfall", tags = "Card Sets")
    void loadAll();

    @GetMapping
    @Operation(summary = "Return all the sets cards available", tags = "Card Sets")
    ResponseEntity<List<SetResponse>> getAll();

    @GetMapping("/{setCode}")
    @Operation(summary = "Return all the cards from a given set", tags = "Card Sets")
    ResponseEntity<List<CardResponse>> getCards(@PathVariable String setCode);

}
