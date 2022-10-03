package fr.ignishky.mtgcollection.domain.set;

import fr.ignishky.mtgcollection.domain.set.event.SetAdded;
import fr.ignishky.mtgcollection.domain.set.event.SetUpdated;
import fr.ignishky.mtgcollection.framework.domain.Aggregate;
import fr.ignishky.mtgcollection.framework.domain.AppliedEvent;
import io.vavr.control.Option;
import lombok.With;

@With
public record Set(
        SetId id,
        SetCode code,
        SetName name,
        boolean isDigital,
        Option<SetCode> parentSetCode,
        Option<SetCode> blockCode,
        String releasedDate,
        SetType setType,
        Integer cardCount,
        Integer cardOwnedCount,
        SetIcon icon
) implements Aggregate<SetId>, Comparable<Set> {

    public static AppliedEvent<Set, SetAdded> add(
            SetId id,
            SetCode code,
            SetName name,
            Option<SetCode> parentSetCode,
            Option<SetCode> blockCode,
            String releasedDate,
            SetType setType,
            int cardCount,
            SetIcon icon
    ) {
        SetAdded setAdded = new SetAdded(id, code, name, parentSetCode, blockCode, releasedDate, setType, cardCount, icon);
        Set set = setAdded.apply(null);
        return new AppliedEvent<>(set, setAdded);
    }

    public AppliedEvent<Set, SetUpdated> incrementCardOwned() {
        SetUpdated setUpdated = new SetUpdated(id, cardOwnedCount + 1);
        Set set = setUpdated.apply(this);
        return new AppliedEvent<>(set, setUpdated);
    }

    public AppliedEvent<Set, SetUpdated> decrementCardOwned() {
        SetUpdated setUpdated = new SetUpdated(id, cardOwnedCount - 1);
        Set set = setUpdated.apply(this);
        return new AppliedEvent<>(set, setUpdated);
    }

    @Override
    public int compareTo(Set set) {
        return setType.compareTo(set.setType);
    }

}
