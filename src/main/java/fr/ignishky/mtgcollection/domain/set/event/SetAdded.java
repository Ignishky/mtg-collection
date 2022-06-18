package fr.ignishky.mtgcollection.domain.set.event;

import fr.ignishky.mtgcollection.domain.set.*;
import fr.ignishky.mtgcollection.framework.common.Instants;
import fr.ignishky.mtgcollection.framework.cqrs.event.Event;
import io.vavr.control.Option;

import java.time.Instant;

public class SetAdded extends Event<SetId, Set, SetAdded.SetAddedPayload> {

    private final SetCode code;
    private final SetName name;
    private final String releaseDate;
    private final Option<SetCode> parentSetCode;
    private final Option<SetCode> blockCode;
    private final int cardCount;
    private final SetIcon icon;

    public SetAdded(SetId setId, SetCode code, SetName name, Option<SetCode> parentSetCode, Option<SetCode> blockCode, String releaseDate, int cardCount, SetIcon icon) {
        this(null, setId, code, name, parentSetCode, blockCode, releaseDate, cardCount, icon, Instants.now());
    }

    private SetAdded(String id, SetId aggregateId, SetCode code, SetName name, Option<SetCode> parentSetCode, Option<SetCode> blockCode, String releaseDate, int cardCount, SetIcon icon, Instant instant) {
        super(id, aggregateId, Set.class, new SetAddedPayload(code.value(), name.value(), parentSetCode.map(SetCode::value).getOrNull(), blockCode.map(SetCode::value).getOrNull(), releaseDate, cardCount, icon.url()), instant);
        this.code = code;
        this.name = name;
        this.parentSetCode = parentSetCode;
        this.blockCode = blockCode;
        this.releaseDate = releaseDate;
        this.cardCount = cardCount;
        this.icon = icon;
    }

    @Override
    public Set apply(Set set) {
        return new Set(aggregateId(), code, name, false, parentSetCode, blockCode, releaseDate, cardCount, icon);
    }

    public record SetAddedPayload(
            String code,
            String name,
            String parentSetCode,
            String blockCode,
            String releaseDate,
            int cardCount,
            String icon
    ) implements Event.Payload {
    }

}
