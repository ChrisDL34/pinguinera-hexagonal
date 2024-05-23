package com.penguin.model.generic;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

public abstract class DomainEvent implements Serializable {

    public Instant when;
    public UUID uuid;
    public String type;
    private String aggregateRootId;
    private String aggregate;
    private Long versionType;

    public DomainEvent() {
    }

    public DomainEvent(String type) {
        this.type = type;
        this.aggregate = "default";
        this.when = Instant.now();
        this.uuid = UUID.randomUUID();
        this.versionType = 1L;
    }

    public DomainEvent(Instant when, UUID uuid, String type, String aggregateRootId, String aggregate, Long versionType) {
        this.when = when;
        this.uuid = uuid;
        this.type = type;
        this.aggregateRootId = aggregateRootId;
        this.aggregate = aggregate;
        this.versionType = versionType;
    }

    public Long versionType(){
        return versionType;
    }

    public void setVersionType(Long versionType){
        this.versionType = versionType;
    }

    public String aggregateRootId(){
        return  aggregateRootId;
    }
    public void setAggregateRootId(String aggregateRootId){
        this.aggregateRootId = aggregateRootId;
    }

    public String aggregateName(){
        return aggregate;
    }

    public void setAggregateName(String aggregateName){
        this.aggregate = aggregateName;
    }
}
