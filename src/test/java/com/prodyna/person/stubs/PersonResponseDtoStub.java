package com.prodyna.person.stubs;

import com.prodyna.person.dto.PersonResponseDto;

public class PersonResponseDtoStub {

    public static PersonResponseDto getDto() {
        return PersonResponseDto.builder()
                .personId("5087fb1f-8d57-46e0-9cdb-ad70855f0fc4")
                .name("name").build();
    }
}
