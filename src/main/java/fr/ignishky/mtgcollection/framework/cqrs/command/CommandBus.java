package fr.ignishky.mtgcollection.framework.cqrs.command;

import io.vavr.control.Try;

public interface CommandBus {

    <T> Try<T> dispatch(Command<T> message);

}
