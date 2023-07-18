package com.prodyna.person.mapper;

import com.prodyna.person.dto.PersonRequestDto;
import com.prodyna.person.dto.PersonResponseDto;
import com.prodyna.person.model.Person;
import com.prodyna.person.stubs.PersonModelStub;
import com.prodyna.person.stubs.PersonRequestDtoStub;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

class PersonMapperTest {

    private final PersonMapper mapper = Mappers.getMapper(PersonMapper.class);

    @Test
    void shouldMapToPersonResponse() {
        Person person = PersonModelStub.getPerson();

        PersonResponseDto personResponse = mapper.toPersonResponse(person);

        assertThat(personResponse.getPersonId(), is(person.getId().toString()));
        assertThat(personResponse.getName(), is(person.getName()));
    }

    @Test
    void shouldMapToPersonModel() {
        PersonRequestDto dto = PersonRequestDtoStub.getDto();

        Person person = mapper.toPerson(dto);

        assertThat(person.getName(), is(dto.getName()));
    }
}
