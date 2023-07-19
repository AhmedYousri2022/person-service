package com.prodyna.person.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.prodyna.person.dto.PersonRequestDto;
import com.prodyna.person.dto.PersonResponseDto;
import com.prodyna.person.exception.BadRequestException;
import com.prodyna.person.exception.NotFoundException;
import com.prodyna.person.mapper.PersonMapper;
import com.prodyna.person.model.Person;
import com.prodyna.person.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository repository;

    private final PersonMapper personMapper = Mappers.getMapper(PersonMapper.class);

    @Transactional
    public PersonResponseDto addPerson(PersonRequestDto dto) {
        Person person = repository.save(personMapper.toPerson(dto));
        return personMapper.toPersonResponse(person);
    }

    @Transactional
    public PersonResponseDto updatePerson(String personId, PersonRequestDto personRequestDto) {
        Person person = getPersonIfExist(personId);
        person.setName(personRequestDto.getName());
        Person updatedPerson = repository.save(person);
        return personMapper.toPersonResponse(updatedPerson);
    }

    @Cacheable(value = "person")
    @Transactional(readOnly = true)
    public PersonResponseDto getPersonDetails(String personId) {
        Person person = getPersonIfExist(personId);
        return personMapper.toPersonResponse(person);
    }


    @Transactional(readOnly = true)
    public List<PersonResponseDto> getAllPersons() {
        List<Person> all = repository.findAll();
        return all.stream().map(personMapper::toPersonResponse).collect(Collectors.toList());
    }

    @Transactional
    public void deletePerson(String personId) {
        Person person = getPersonIfExist(personId);
        repository.deleteById(person.getId());
    }

    private Person getPersonIfExist(String personId) {
        UUID uuid;
        try {
            uuid = UUID.fromString(personId);
        } catch (IllegalArgumentException e) {
            log.error("invalid id format with id {}", personId);
            throw new BadRequestException("Person not found");
        }
        return repository.findById(uuid).orElseThrow(() -> new NotFoundException("Person not found with id " + personId + " "));
    }
}
