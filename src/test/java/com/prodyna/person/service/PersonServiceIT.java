package com.prodyna.person.service;

import java.util.List;

import com.prodyna.person.dto.PersonRequestDto;
import com.prodyna.person.dto.PersonResponseDto;
import com.prodyna.person.exception.NotFoundException;
import com.prodyna.person.model.Person;
import com.prodyna.person.repository.PersonRepository;
import com.prodyna.person.stubs.PersonModelStub;
import com.prodyna.person.stubs.PersonRequestDtoStub;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class PersonServiceIT {

    @Autowired
    private PersonRepository repository;

    @Autowired
    private PersonService service;

    @AfterEach
    void cleanup() {
        repository.deleteAll();
    }

    @Test
    void shouldCreatePerson() {
        PersonResponseDto responseDto = service.addPerson(PersonRequestDtoStub.getDto());

        assertThat(responseDto.getName(), is("name"));
    }

    @Test
    void shouldThrowPersonNotfound() {
        Exception exception = assertThrows(
                NotFoundException.class,
                () -> service.updatePerson("5087fb1f-8d57-46e0-9cdb-ad70855f0fc4", PersonRequestDtoStub.getDto()),
                "Person not found");

        assertThat(exception.getMessage(), is("Person not found"));
    }

    @Test
    void shouldUpdateName() {
        Person person = repository.save(PersonModelStub.getPerson());
        PersonRequestDto dto = PersonRequestDtoStub.getDto();
        dto.setName("newName");
        PersonResponseDto responseDto = service.updatePerson(String.valueOf(person.getId()), dto);

        assertThat(responseDto.getName(), is("newName"));
    }

    @Test
    void shouldGetAllPersons() {
        addPersonsToDb();

        List<PersonResponseDto> responseDto = service.getAllPersons();

        assertThat(responseDto, hasSize(3));
    }

    @Test
    void shouldGetPersonDetails() {
        Person person = repository.save(PersonModelStub.getPerson());

        PersonResponseDto personDetails = service.getPersonDetails(String.valueOf(person.getId()));

        assertThat(personDetails.getName(), is(person.getName()));
    }

    @Test
    void shouldDeletePerson() {
        Person person = repository.save(PersonModelStub.getPerson());

        service.deletePerson(String.valueOf(person.getId()));

        assertThat(repository.findAll(), hasSize(0));
    }

    private void addPersonsToDb() {
        repository.save(PersonModelStub.getPerson());
        repository.save(PersonModelStub.getPerson());
        repository.save(PersonModelStub.getPerson());
    }
}
