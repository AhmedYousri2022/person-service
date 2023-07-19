package com.prodyna.person.stubs;

import com.prodyna.person.model.Person;

public class PersonModelStub {

    public static Person getPerson() {
        return Person.builder()
                .name("name").build();
    }
}
