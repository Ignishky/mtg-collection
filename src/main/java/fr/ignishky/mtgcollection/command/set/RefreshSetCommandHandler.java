package fr.ignishky.mtgcollection.command.set;

import fr.ignishky.mtgcollection.domain.set.Set;
import fr.ignishky.mtgcollection.domain.set.SetReferer;
import fr.ignishky.mtgcollection.domain.set.SetRepository;
import fr.ignishky.mtgcollection.framework.cqrs.command.CommandHandler;
import fr.ignishky.mtgcollection.framework.cqrs.command.CommandResponse;
import io.vavr.collection.List;
import org.springframework.stereotype.Component;

import static fr.ignishky.mtgcollection.framework.cqrs.command.CommandResponse.toCommandResponse;

@Component
public record RefreshSetCommandHandler(
        SetReferer referer,
        SetRepository repository
) implements CommandHandler<RefreshSetCommand, Void> {

    @Override
    public CommandResponse<Void> handle(RefreshSetCommand command) {
        List<Set> sets = referer.loadAll()
                .filter(Set::hasBeenReleased);

        repository.save(sets);

        var events = sets.map(Set::toAddedEvent);

        return toCommandResponse(events);
    }

}
