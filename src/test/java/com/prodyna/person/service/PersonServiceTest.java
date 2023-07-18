package com.prodyna.person.service;

import java.util.List;
import java.util.Optional;

import com.prodyna.person.dto.PersonResponseDto;
import com.prodyna.person.repository.PersonRepository;
import com.prodyna.person.stubs.PersonModelStub;
import com.prodyna.person.stubs.PersonRequestDtoStub;
import com.prodyna.person.stubs.PersonResponseDtoStub;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

    @Mock
    private PersonRepository repository;

    @InjectMocks
    private PersonService service;


    @Test
    void shouldAddPerson() {
        when(repository.save(any())).thenReturn(PersonModelStub.getPerson());

        PersonResponseDto responseDto = service.addPerson(PersonRequestDtoStub.getDto());

        assertThat(responseDto.getName(), is("name"));
    }

    @Test
    void shouldGetPersonDetails() {
        when(repository.findById(any())).thenReturn(Optional.of(PersonModelStub.getPerson()));

        PersonResponseDto responseDto = service.getPersonDetails(PersonResponseDtoStub.getDto().getPersonId());

        assertThat(responseDto.getName(), is("name"));
    }

    @Test
    void shouldAllPersons() {
        when(repository.findAll()).thenReturn(List.of(PersonModelStub.getPerson()));

        List<PersonResponseDto> responseDtos = service.getAllPersons();

        assertThat(responseDtos, hasSize(1));
    }
}
