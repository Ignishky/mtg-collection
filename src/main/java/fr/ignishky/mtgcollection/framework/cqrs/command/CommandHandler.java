package fr.ignishky.mtgcollection.framework.cqrs.command;

import java.lang.reflect.ParameterizedType;

public interface CommandHandler<C extends Command<R>, R> {

    CommandResponse<R> handle(C message);

    default Class<C> listenTo() {
        return (Class<C>) ((ParameterizedType) getClass().getGenericInterfaces()[0]).getActualTypeArguments()[0];
    }
}
