package fr.ignishky.mtgcollection.domain.set;

import fr.ignishky.mtgcollection.framework.domain.AppliedEvent;
import fr.ignishky.mtgcollection.domain.set.event.SetAdded;
import fr.ignishky.mtgcollection.framework.domain.Aggregate;

import static java.time.LocalDate.now;
import static java.time.LocalDate.parse;

public record Set(
        SetId id,
        SetCode code,
        SetName name,
        boolean isDigital,
        String releasedDate,
        int cardCount,
        SetIcon icon
) implements Aggregate<SetId> {

    public boolean hasBeenReleased() {
        return parse(releasedDate).isBefore(now());
    }

    public boolean hasCard() {
        return 0 < cardCount;
    }

    public boolean isNotDigital() {
        return !isDigital;
    }

    public static AppliedEvent<Set, SetAdded> add(SetId id, SetCode code, SetName name, String releasedDate, int cardCount, SetIcon icon) {
        SetAdded setAdded = new SetAdded(id, code, name, releasedDate, cardCount, icon);
        Set set = setAdded.apply(null);
        return new AppliedEvent<>(set, setAdded);
    }

}
