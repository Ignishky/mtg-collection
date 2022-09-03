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

    static Set toSet(SetScryfallData setScryfallData) {
        return new Set(toSetId(setScryfallData.id()),
                new SetCode(setScryfallData.code()),
                new SetName(setScryfallData.name()),
                setScryfallData.digital(),
                Option.of(setScryfallData.parent_set_code()).map(SetCode::new),
                Option.of(setScryfallData.block_code()).map(SetCode::new),
                setScryfallData.released_at(),
                SetType.valueOf(setScryfallData.set_type()),
                setScryfallData.card_count(),
                new SetIcon(setScryfallData.icon_svg_uri()));
    }

    static Card toCard(CardScryfallData cardScryfallData) {
        return new Card(
                new CardId(cardScryfallData.id()),
                new SetCode(cardScryfallData.set()),
                new CardName(cardScryfallData.name()),
                new CardImage(cardScryfallData.image_uris() != null
                        ? cardScryfallData.image_uris().normal()
                        : cardScryfallData.card_faces().get(0).image_uris() != null
                        ? cardScryfallData.card_faces().get(0).image_uris().normal()
                        : null),
                false,
                false);
    }

}
