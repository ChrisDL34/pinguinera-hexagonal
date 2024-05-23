package com.penguin.model.generic;
import java.util.List;

public abstract class AggregateRoot<I extends Identity> extends Entity<I>{

    private final ChangeEventSubscriber changeEventSubscriber;

    public AggregateRoot(I id) {
        super(id);
        this.changeEventSubscriber = new ChangeEventSubscriber();
    }

    public final void subscribe(EventChange event){changeEventSubscriber.subscribe(event);}

    protected void applyEvent(DomainEvent event){
        changeEventSubscriber.applyEvent(event);
    }

    protected ChangeApply appendChange(DomainEvent domainEvent){
        var nameClass = identity().getClass().getSimpleName();
        var aggregate = nameClass.replaceAll("Identity|Id|ID", "").toLowerCase();
        domainEvent.setAggregateName(aggregate);
        domainEvent.setAggregateRootId(identity().value());
        return changeEventSubscriber.appendChange(domainEvent);
    }

    public List<DomainEvent> getUncommittedChanges(){
        return List.copyOf(changeEventSubscriber.changes());
    }

    public void markChangeAsCommitted(){
        changeEventSubscriber.changes().clear();
    }

}
