package com.prodyna.person.stubs;

import java.util.UUID;

import com.prodyna.person.model.Person;

public class PersonModelStub {

    public static Person getPerson() {
        return Person.builder()
                .id(UUID.fromString("5087fb1f-8d57-46e0-9cdb-ad70855f0fc4"))
                .name("name").build();
    }
}
