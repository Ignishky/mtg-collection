package fr.ignishky.mtgcollection.command.set;

import fr.ignishky.mtgcollection.domain.AppliedEvent;
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

        List<Set> existingSets = repository.getAll();

        var newSets = referer.loadAll()
                .filter(Set::hasBeenReleased)
                .filter(set -> !existingSets.contains(set))
                .map(set -> Set.add(set.id(), set.code(), set.name(), set.releasedDate(), set.icon()));

        repository.save(newSets.map(AppliedEvent::aggregate));
        return toCommandResponse(newSets.map(AppliedEvent::event));
    }

}
