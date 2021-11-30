package fr.ignishky.mtgcollection.domain.set.event;

import fr.ignishky.mtgcollection.domain.set.*;
import fr.ignishky.mtgcollection.framework.common.Instants;
import fr.ignishky.mtgcollection.framework.cqrs.event.Event;

import java.time.Instant;

public class SetAdded extends Event<SetId, Set, SetAdded.Payload> {

    public SetAdded(SetId setId, SetCode code, SetName name, SetIcon icon) {
        this(null, setId, code, name, icon, Instants.now());
    }

    private SetAdded(String id, SetId setId, SetCode code, SetName name, SetIcon icon, Instant instant) {
        super(id, setId, Set.class, new Payload(code.value(), name.value(), icon.url()), instant);
    }

    public static record Payload(
            String code,
            String name,
            String icon
    ) implements Event.Payload {
    }

}
