package fr.ignishky.mtgcollection.infrastructure.spi.scryfall.model;

import fr.ignishky.mtgcollection.domain.card.Card;
import fr.ignishky.mtgcollection.domain.card.CardId;
import fr.ignishky.mtgcollection.domain.card.CardImage;
import fr.ignishky.mtgcollection.domain.card.CardName;
import fr.ignishky.mtgcollection.domain.set.SetCode;
import io.vavr.collection.List;

import java.util.UUID;

public record CardScryfall(
        String next_page,
        List<ScryfallData> data
) {

    public record ScryfallData(
            UUID id,
            String name,
            String set,
            Images image_uris,
            List<CardFaces> card_faces
    ) {

        public Card toCard() {
            return new Card(
                    new CardId(id),
                    new SetCode(set),
                    new CardName(name),
                    new CardImage(image_uris != null
                            ? image_uris.normal
                            : card_faces.get(0).image_uris != null
                            ? card_faces.get(0).image_uris.normal
                            : null),
                    false,
                    false);
        }

        public record Images(String normal) {

        }

        public record CardFaces(Images image_uris) {

        }

    }

}
