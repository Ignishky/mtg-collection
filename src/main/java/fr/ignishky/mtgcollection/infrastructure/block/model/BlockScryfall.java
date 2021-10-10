package fr.ignishky.mtgcollection.infrastructure.block.model;

import io.vavr.collection.List;

public record BlockScryfall(List<ScryfallData> data) {
    public record ScryfallData(String id, String code, String name, String parent_set_code) {
    }
}
