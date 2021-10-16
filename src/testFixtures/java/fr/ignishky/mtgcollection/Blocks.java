package fr.ignishky.mtgcollection;

import fr.ignishky.mtgcollection.domain.block.model.Block;

import static fr.ignishky.mtgcollection.domain.block.model.Block.toBlock;

public class Blocks {

    public static final Block SCOURGE = toBlock("5133c3a1-1412-4ce6-a1f0-73b695966ded", "scg", "Scourge");
    public static final Block ADVENTURE_OF_THE_FORGOTTEN_REALMS = toBlock("e1ef87ba-ba92-4573-817f-543b996d2851", "afr", "Adventures in the Forgotten Realms");
    public static final Block INNISTRAD_MIDNIGHT_HUNT = toBlock("44b8eb8f-fa23-401a-98b5-1fbb9871128e", "mid", "Innistrad: Midnight hunt");
    public static final Block CORE_SET_2020_TOKENS = toBlock("f291a61b-4afa-4c57-85ac-c67d5ab1403d", "tm20", "Core Set 2020 Tokens");
}
