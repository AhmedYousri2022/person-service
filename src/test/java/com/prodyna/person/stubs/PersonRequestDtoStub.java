package com.prodyna.person.stubs;

import com.prodyna.person.dto.PersonRequestDto;

public class PersonRequestDtoStub {

    public static PersonRequestDto getDto() {
        return PersonRequestDto.builder()
                .name("name")
                .build();
    }
}
