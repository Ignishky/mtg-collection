package fr.ignishky.mtgcollection.domain.block.model;

import fr.ignishky.mtgcollection.domain.shared.Aggregate;

import java.util.UUID;

public record Block(BlockId id, String code, String name) implements Aggregate<Block.BlockId> {

    public static Block toBlock(UUID id, String code, String name) {
        return new Block(new BlockId(id), code, name);
    }

    public static Block toBlock(String id, String code, String name) {
        return toBlock(UUID.fromString(id), code, name);
    }

    public record BlockId(UUID uuid) {
    }
}
