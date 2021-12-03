package fr.ignishky.mtgcollection.infrastructure.api.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.vavr.collection.List;
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
    @Operation(summary = "Return all the sets cards available", tags = "Cards Sets")
    List<SetResponse> getAll();
}
