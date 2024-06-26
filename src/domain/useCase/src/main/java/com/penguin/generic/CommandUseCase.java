package com.penguin.generic;

import com.penguin.model.generic.Command;
import com.penguin.model.generic.DomainEvent;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;


public abstract class CommandUseCase <R extends Command> implements Function<Mono<R>, Flux<DomainEvent>> {
    public abstract Flux<DomainEvent> apply(Mono<R> rMono);
}
