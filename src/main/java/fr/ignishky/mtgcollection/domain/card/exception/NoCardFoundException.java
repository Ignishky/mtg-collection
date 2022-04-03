package fr.ignishky.mtgcollection.domain.card.exception;

import fr.ignishky.mtgcollection.domain.card.CardId;

public class NoCardFoundException extends RuntimeException {

    private final CardId cardId;

    public NoCardFoundException(CardId cardId) {
        this.cardId = cardId;
    }

}
