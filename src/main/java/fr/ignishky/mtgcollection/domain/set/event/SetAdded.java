package fr.ignishky.mtgcollection.domain.set.event;

import fr.ignishky.mtgcollection.domain.set.Set;
import fr.ignishky.mtgcollection.domain.set.SetCode;
import fr.ignishky.mtgcollection.domain.set.SetId;
import fr.ignishky.mtgcollection.domain.set.SetName;
import fr.ignishky.mtgcollection.framework.common.Instants;
import fr.ignishky.mtgcollection.framework.cqrs.event.Event;

import java.time.Instant;

public class SetAdded extends Event<SetId, Set, SetAdded.Payload> {

    public SetAdded(SetId setId, SetCode code, SetName name) {
        this(null, setId, code, name, Instants.now());
    }

    private SetAdded(String id, SetId setId, SetCode code, SetName name, Instant instant) {
        super(id, setId, Set.class, new Payload(code.value(), name.value()), instant);
    }

    public static record Payload(
            String code,
            String name
    ) implements Event.Payload {
    }

}
