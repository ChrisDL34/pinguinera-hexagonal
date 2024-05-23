package com.penguin.model.bookStoreQuotes.values.identities;

import com.penguin.model.generic.Identity;

public class GroupId extends Identity {

    public GroupId() {
        super();
    }

    public GroupId(String id) {
        super(id);
    }
    public static GroupId of(String uuid){
        return new GroupId(uuid);
    }
}
