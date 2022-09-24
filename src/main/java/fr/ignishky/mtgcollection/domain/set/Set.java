package fr.ignishky.mtgcollection.domain.set;

import fr.ignishky.mtgcollection.domain.set.event.SetAdded;
import fr.ignishky.mtgcollection.framework.domain.Aggregate;
import fr.ignishky.mtgcollection.framework.domain.AppliedEvent;
import io.vavr.control.Option;

public record Set(
        SetId id,
        SetCode code,
        SetName name,
        boolean isDigital,
        Option<SetCode> parentSetCode,
        Option<SetCode> blockCode,
        String releasedDate,
        SetType setType,
        int cardCount,
        SetIcon icon
) implements Aggregate<SetId>, Comparable<Set> {

    public static AppliedEvent<Set, SetAdded> add(SetId id, SetCode code, SetName name, Option<SetCode> parentSetCode, Option<SetCode> blockCode, String releasedDate, SetType setType, int cardCount, SetIcon icon) {
        SetAdded setAdded = new SetAdded(id, code, name, parentSetCode, blockCode, releasedDate, setType, cardCount, icon);
        Set set = setAdded.apply(null);
        return new AppliedEvent<>(set, setAdded);
    }

    @Override
    public int compareTo(Set set) {
        return setType.compareTo(set.setType);
    }

}
