package fr.ignishky.mtgcollection.domain.set;

import fr.ignishky.mtgcollection.domain.AppliedEvent;
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

    public static AppliedEvent<Set, SetAdded> add(SetId id, SetCode code, SetName name, String releasedDate, SetIcon icon) {
        SetAdded setAdded = new SetAdded(id, code, name, releasedDate, icon);
        Set set = setAdded.apply(null);
        return new AppliedEvent<>(set, setAdded);
    }
}
