package fr.ignishky.mtgcollection.domain.set;

import fr.ignishky.mtgcollection.domain.set.event.SetAdded;
import fr.ignishky.mtgcollection.framework.domain.Aggregate;

import static java.time.LocalDate.now;
import static java.time.LocalDate.parse;

public record Set(
        SetId id,
        SetCode code,
        SetName name,
        String releasedDate,
        SetIcon icon
) implements Aggregate<SetId> {

    public static boolean hasBeenReleased(Set set) {
        return parse(set.releasedDate()).isBefore(now());
    }

    public SetAdded toAddedEvent() {
        return new SetAdded(id, code, name, icon);
    }
}
