package fr.ignishky.mtgcollection.infrastructure.api.rest;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
public interface SetApi {

    @PutMapping("/sets")
    @ResponseStatus(NO_CONTENT)
    void loadAll();
}
