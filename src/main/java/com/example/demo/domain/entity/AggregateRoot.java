package com.example.demo.domain.entity;


import com.example.demo.bean.SpringContext;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.example.demo.domain.command.Command;
import com.example.demo.domain.command.CommandFailure;
import com.example.demo.domain.command.CommandHandler;
import com.example.demo.domain.query.Query;
import com.example.demo.domain.query.QueryFailure;
import com.example.demo.domain.query.QueryHandler;
import com.example.demo.event.Event;
import io.vavr.control.Either;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


@SuperBuilder
@EqualsAndHashCode
@NoArgsConstructor
public abstract class AggregateRoot<E extends AggregateRoot<E, I>, I extends Serializable> implements Entity<E, I> {

    @JsonIgnore
    @EqualsAndHashCode.Exclude
    private final AggregateRootBehavior behavior = initialBehavior();
    protected I entityId;

    protected AggregateRoot(I entityId) {

        this.entityId = entityId;
    }

    @Override
    public boolean sameIdentityAs(E other) {

        return other != null && entityId.equals(other.entityId);
    }

    protected AggregateRootBehavior initialBehavior() {

        return null;
    }

    @SuppressWarnings("unchecked")
    public <A extends Command, B extends Event> Either<CommandFailure, B> handle(A command) {

        CommandHandler<A, B> commandHandler = (CommandHandler<A, B>) behavior.commandHandlers.get(command.getClass());
        return commandHandler.handle(command);
    }

    @SuppressWarnings("unchecked")
    public <Q extends Query, R> Either<QueryFailure, R> handle(Q query) {

        QueryHandler<Q, R> queryHandler = (QueryHandler<Q, R>) behavior.queryHandlers.get(query.getClass());
        return queryHandler.handle(query);
    }

    protected <A extends Command, B extends Event, H extends CommandHandler<A, B>> H getHandler(
            Class<H> commandHandlerClass) {

        return SpringContext.getBean(commandHandlerClass);
    }

    protected <Q extends Query, R, H extends QueryHandler<Q, R>> H getQueryHandler(Class<H> queryHandlerClass) {

        return SpringContext.getBean(queryHandlerClass);
    }

    public static class AggregateRootBehavior {

        protected final Map<Class<? extends Command>, CommandHandler<? extends Command, ? extends Event>>
                commandHandlers;
        protected final Map<Class<? extends Query>, QueryHandler<? extends Query, ?>> queryHandlers;

        public AggregateRootBehavior(
                Map<Class<? extends Command>, CommandHandler<? extends Command, ? extends Event>> commandHandlers,
                Map<Class<? extends Query>, QueryHandler<? extends Query, ?>> queryHandlers) {

            this.commandHandlers = Collections.unmodifiableMap(commandHandlers);
            this.queryHandlers = Collections.unmodifiableMap(queryHandlers);
        }
    }

    public class AggregateRootBehaviorBuilder {

        private final Map<Class<? extends Command>, CommandHandler<? extends Command, ? extends Event>>
                commandHandlers = new HashMap<>();
        private final Map<Class<? extends Query>, QueryHandler<? extends Query, ?>> queryHandlers = new HashMap<>();

        public <A extends Command, B extends Event, H extends CommandHandler<A, B>> AggregateRootBehaviorBuilder setCommandHandler(
                Class<A> commandClass, H handler) {

            commandHandlers.put(commandClass, handler);
            return this;
        }

        public <A extends Command, B extends Event, H extends CommandHandler<A, B>> AggregateRootBehaviorBuilder setCommandHandler(
                Class<A> commandClass, Class<H> commandHandlerClass) {

            commandHandlers.put(commandClass, getHandler(commandHandlerClass));
            return this;
        }

        public <Q extends Query, R, H extends QueryHandler<Q, R>> AggregateRootBehaviorBuilder setQueryHandler(
                Class<Q> queryClass, H handler) {

            queryHandlers.put(queryClass, handler);
            return this;
        }

        public <Q extends Query, R, H extends QueryHandler<Q, R>> AggregateRootBehaviorBuilder setQueryHandler(
                Class<Q> queryClass, Class<H> queryHandlerClass) {

            queryHandlers.put(queryClass, getQueryHandler(queryHandlerClass));
            return this;
        }

        public AggregateRootBehavior build() {

            return new AggregateRootBehavior(commandHandlers, queryHandlers);
        }
    }
}
