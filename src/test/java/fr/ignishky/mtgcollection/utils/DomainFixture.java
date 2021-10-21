package fr.ignishky.mtgcollection.utils;

import fr.ignishky.mtgcollection.domain.block.model.Block;
import fr.ignishky.mtgcollection.domain.block.model.Block.BlockId;

import static java.util.UUID.randomUUID;

public class DomainFixture {

    public static Block block(int number) {
        return new Block(new BlockId(randomUUID()), "code%s".formatted(number), "name%s".formatted(number), "icon%s".formatted(number));
    }
}
