package fr.ignishky.mtgcollection.infrastructure.spi.scryfall;

import fr.ignishky.mtgcollection.domain.card.Card;
import fr.ignishky.mtgcollection.domain.card.CardId;
import fr.ignishky.mtgcollection.domain.card.CardImage;
import fr.ignishky.mtgcollection.domain.card.CardName;
import fr.ignishky.mtgcollection.domain.set.*;
import fr.ignishky.mtgcollection.infrastructure.spi.scryfall.model.CardScryfallData;
import fr.ignishky.mtgcollection.infrastructure.spi.scryfall.model.SetScryfallData;
import io.vavr.control.Option;

import static fr.ignishky.mtgcollection.domain.set.SetId.toSetId;

class ScryfallMapper {

    private ScryfallMapper() {
    }

    static Set toSet(SetScryfallData setScryfallData) {
        return new Set(toSetId(setScryfallData.id()),
                new SetCode(setScryfallData.code()),
                new SetName(setScryfallData.name()),
                setScryfallData.digital(),
                Option.of(setScryfallData.parentSetCode()).map(SetCode::new),
                Option.of(setScryfallData.blockCode()).map(SetCode::new),
                setScryfallData.releasedAt(),
                SetType.valueOf(setScryfallData.setType()),
                setScryfallData.card_count(),
                new SetIcon(setScryfallData.iconSvgUri()));
    }

    static Card toCard(CardScryfallData cardScryfallData) {
        return new Card(
                new CardId(cardScryfallData.id()),
                new SetCode(cardScryfallData.set()),
                new CardName(cardScryfallData.name()),
                new CardImage(cardScryfallData.imageUris() != null
                        ? cardScryfallData.imageUris().normal()
                        : cardScryfallData.cardFaces().get(0).imageUris() != null
                        ? cardScryfallData.cardFaces().get(0).imageUris().normal()
                        : null),
                false,
                false);
    }

}
