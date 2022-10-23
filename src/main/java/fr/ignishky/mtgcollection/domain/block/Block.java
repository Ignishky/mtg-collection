package fr.ignishky.mtgcollection.domain.block;

public record Block(
        BlockCode code,
        BlockName name,
        int nbCards,
        int nbOwned,
        int nbOwnedFoil,
        BlockIcon icon
) {

}
