package com.prodyna.person.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.prodyna.person.dto.PersonRequestDto;
import com.prodyna.person.dto.PersonResponseDto;
import com.prodyna.person.service.PersonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/persons")
@Api(value = "Person service", tags = {"Person"})
public class PersonController {

    private final PersonService service;

    @ApiOperation(value = "Add new Person")
    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping(consumes = "application/json", produces = "application/json")
    public PersonResponseDto addPerson(@Valid @RequestBody PersonRequestDto personRequestDto) {
        return service.addPerson(personRequestDto);
    }

    @ApiOperation(value = "get all persons")
    @GetMapping(consumes = "application/json", produces = "application/json")
    public List<PersonResponseDto> getAllPersons() {
        return service.getAllPersons();
    }

    @ApiOperation(value = "get person details")
    @GetMapping(path = "/{personId}", consumes = "application/json", produces = "application/json")
    public PersonResponseDto getPersonDetails(@NotNull @PathVariable String personId) {
        return service.getPersonDetails(personId);
    }

    @ApiOperation(value = "Change a name of a person")
    @PutMapping(path = "/{personId}", consumes = "application/json", produces = "application/json")
    public PersonResponseDto updatePerson(@NotNull @PathVariable String personId,
                                          @Valid @RequestBody PersonRequestDto personRequestDto) {
        return service.updatePerson(personId, personRequestDto);
    }


    @ApiOperation(value = "delete person")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @DeleteMapping(path = "/{personId}", consumes = "application/json", produces = "application/json")
    public void deletePerson(@Valid @PathVariable String personId) {
        service.deletePerson(personId);
    }
}
