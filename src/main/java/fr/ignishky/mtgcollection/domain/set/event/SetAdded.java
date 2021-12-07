package fr.ignishky.mtgcollection.domain.set.event;

import fr.ignishky.mtgcollection.domain.set.*;
import fr.ignishky.mtgcollection.framework.common.Instants;
import fr.ignishky.mtgcollection.framework.cqrs.event.Event;

import java.time.Instant;

public class SetAdded extends Event<SetId, Set, SetAdded.Payload> {

    private final SetCode code;
    private final SetName name;
    private final String releaseDate;
    private final Integer cardCount;
    private final SetIcon icon;

    public SetAdded(SetId setId, SetCode code, SetName name, String releaseDate, Integer cardCount, SetIcon icon) {
        this(null, setId, code, name, releaseDate, cardCount, icon, Instants.now());
    }

    private SetAdded(String id, SetId aggregateId, SetCode code, SetName name, String releaseDate, Integer cardCount, SetIcon icon, Instant instant) {
        super(id, aggregateId, Set.class, new Payload(code.value(), name.value(), releaseDate, cardCount, icon.url()), instant);
        this.code = code;
        this.name = name;
        this.releaseDate = releaseDate;
        this.cardCount = cardCount;
        this.icon = icon;
    }

    @Override
    public Set apply(Set set) {
        return new Set(aggregateId(), code, name, releaseDate, cardCount, icon);
    }

    public record Payload(
            String code,
            String name,
            String releaseDate,
            Integer cardCount,
            String icon
    ) implements Event.Payload {
    }

}
