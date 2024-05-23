package com.penguin.model.bookStoreQuotes.values.identities;

import com.penguin.model.generic.Identity;

public class GroupmenId extends Identity {

    public GroupmenId() {
        super();
    }

    public GroupmenId(String id) {
        super(id);
    }
    public static GroupmenId of(String uuid){
        return new GroupmenId(uuid);
    }
}
