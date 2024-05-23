package com.penguin.model.generic;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

public class ChangeEventSubscriber {

    private final List<DomainEvent> changes = new LinkedList<>();

    private final Map<String, AtomicLong> versions = new ConcurrentHashMap<>();

    private final Set<Consumer<? super DomainEvent>> observables = new HashSet<>();

    public List<DomainEvent> changes(){
        return changes;
    }

    public final void subscribe(EventChange eventChange){
        this.observables.addAll(eventChange.behaviors);
    }

    public final ChangeApply appendChange(DomainEvent event){
        changes.add(event);
        return () -> applyEvent(event);
    }

    public final void applyEvent(DomainEvent domainEvent){
        observables.forEach(consumer -> {
            try{
                consumer.accept(domainEvent);
                var map = versions.get(domainEvent.type);
                long version = nextVersion(domainEvent, map);
                domainEvent.setVersionType(version);
            }catch (ClassCastException ignored){}
        });
    }

    private long nextVersion (DomainEvent event, AtomicLong map){
        if (map == null){
            versions.put(event.type, new AtomicLong(event.versionType()));
            return event.versionType();
        }
        return versions.get(event.type).incrementAndGet();
    }
}
