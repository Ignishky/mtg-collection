package fr.ignishky.mtgcollection.domain.set.event;

import fr.ignishky.mtgcollection.domain.set.model.*;
import fr.ignishky.mtgcollection.framework.cqrs.event.Event;
import fr.ignishky.mtgcollection.framework.cqrs.event.Payload;
import io.vavr.control.Option;

import java.time.Instant;

import static fr.ignishky.mtgcollection.framework.common.Instants.now;

public class SetAdded extends Event<SetId, Set, SetAdded.SetAddedPayload> {

    private final SetCode code;
    private final SetName name;
    private final String releaseDate;
    private final SetType setType;
    private final Option<SetCode> parentSetCode;
    private final Option<SetCode> blockCode;
    private final int cardCount;
    private final SetIcon icon;

    public SetAdded(
            SetId aggregateId,
            SetCode code,
            SetName name,
            Option<SetCode> parentSetCode,
            Option<SetCode> blockCode,
            String releaseDate,
            SetType setType,
            int cardCount,
            SetIcon icon
    ) {
        this(null, aggregateId, code, name, parentSetCode, blockCode, releaseDate, setType, cardCount, icon, now());
    }

    private SetAdded(
            String id,
            SetId aggregateId,
            SetCode code,
            SetName name,
            Option<SetCode> parentSetCode,
            Option<SetCode> blockCode,
            String releaseDate,
            SetType setType,
            int cardCount,
            SetIcon icon,
            Instant instant
    ) {
        super(
                id,
                aggregateId,
                Set.class,
                new SetAddedPayload(
                        code.value(),
                        name.value(),
                        parentSetCode.map(SetCode::value).getOrNull(),
                        blockCode.map(SetCode::value).getOrNull(),
                        releaseDate,
                        setType.name(),
                        cardCount,
                        icon.url()
                ),
                instant
        );
        this.code = code;
        this.name = name;
        this.parentSetCode = parentSetCode;
        this.blockCode = blockCode;
        this.releaseDate = releaseDate;
        this.setType = setType;
        this.cardCount = cardCount;
        this.icon = icon;
    }

    @Override
    public Set apply(Set aggregate) {
        return new Set(aggregateId(), code, name, false, parentSetCode, blockCode, releaseDate, setType, cardCount, 0, 0, icon);
    }

    record SetAddedPayload(
            String code,
            String name,
            String parentSetCode,
            String blockCode,
            String releaseDate,
            String setType,
            int cardCount,
            String icon
    ) implements Payload {

    }

}
