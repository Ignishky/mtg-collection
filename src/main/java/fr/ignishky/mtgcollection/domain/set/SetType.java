package fr.ignishky.mtgcollection.domain.set;

public enum SetType implements Comparable<SetType>{
    // sorted
    expansion,
    memorabilia,
    token,
    promo,

    //non-sorted,
    alchemy,
    archenemy,
    arsenal,
    box,
    commander,
    core,
    draft_innovation,
    duel_deck,
    from_the_vault,
    funny,
    masters,
    masterpiece,
    planechase,
    premium_deck,
    spellbook,
    starter,
    treasure_chest,
    vanguard
}
