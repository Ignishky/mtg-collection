package fr.ignishky.mtgcollection.infrastructure.spi.scryfall;

import fr.ignishky.mtgcollection.domain.card.*;
import fr.ignishky.mtgcollection.domain.set.SetCode;
import fr.ignishky.mtgcollection.infrastructure.spi.scryfall.model.CardScryfallData;
import io.vavr.collection.List;

import java.time.LocalDate;

class ScryfallMapper {

    private ScryfallMapper() {
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
                List.of(new Price(LocalDate.now(), cardScryfallData.prices().eur(), cardScryfallData.prices().eurFoil())),
                false,
                false);
    }

}
