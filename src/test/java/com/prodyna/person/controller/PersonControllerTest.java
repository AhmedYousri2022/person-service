package com.prodyna.person.controller;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prodyna.person.config.SecurityConfiguration;
import com.prodyna.person.dto.PersonRequestDto;
import com.prodyna.person.dto.PersonResponseDto;
import com.prodyna.person.exception.DelegatedAuthenticationEntryPoint;
import com.prodyna.person.exception.NotFoundException;
import com.prodyna.person.service.PersonService;
import com.prodyna.person.stubs.PersonRequestDtoStub;
import com.prodyna.person.stubs.PersonResponseDtoStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PersonController.class)
@ComponentScan(basePackages = "com.prodyna.person")
@ContextConfiguration(classes = {SecurityConfiguration.class, DelegatedAuthenticationEntryPoint.class})
class PersonControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PersonService service;

    private PersonRequestDto personRequestDto;

    @BeforeEach
    void setup() {
        personRequestDto = PersonRequestDtoStub.getDto();
    }

    @Test
    void shouldCreatePerson() throws Exception {
        given(service.addPerson(personRequestDto)).willReturn(PersonResponseDtoStub.getDto());

        mvc.perform(post("/persons")
                            .with(httpBasic("admin", "admin"))
                            .accept(APPLICATION_JSON)
                            .contentType(APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(personRequestDto)))

                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.personId").exists())
                .andExpect(jsonPath("$.name").exists());
    }

    @Test
    void shouldGetPersonDetails() throws Exception {
        given(service.getPersonDetails("5087fb1f-8d57-46e0-9cdb-ad70855f0fc4"))
                .willReturn(PersonResponseDtoStub.getDto());

        mvc.perform(get("/persons/5087fb1f-8d57-46e0-9cdb-ad70855f0fc4")
                            .with(httpBasic("admin", "admin"))
                            .accept(APPLICATION_JSON)
                            .contentType(APPLICATION_JSON)
                   )

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.personId").exists())
                .andExpect(jsonPath("$.name").isNotEmpty());
    }

    @Test
    void shouldGetAllPersons() throws Exception {
        given(service.getAllPersons())
                .willReturn(List.of(PersonResponseDtoStub.getDto()));

        mvc.perform(get("/persons")
                            .with(httpBasic("admin", "admin"))
                            .accept(APPLICATION_JSON)
                            .contentType(APPLICATION_JSON)
                   )

                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].personId").exists())
                .andExpect(jsonPath("$[0].name").isNotEmpty());
    }

    @Test
    void shouldThrowPersonNotFound() throws Exception {
        doThrow(new NotFoundException("person not found")).when(service).getPersonDetails("ad70855f0fc4");

        mvc.perform(get("/persons/ad70855f0fc4")
                            .with(httpBasic("admin", "admin"))
                            .accept(APPLICATION_JSON)
                            .contentType(APPLICATION_JSON)
                   )

                .andExpect(status().isNotFound())
                .andExpect(jsonPath("message").exists())
                .andExpect(jsonPath("message").value("person not found"));
    }

    @Test
    void shouldUpdatePersonName() throws Exception {
        PersonResponseDto dto = PersonResponseDtoStub.getDto();
        dto.setName("newName");
        personRequestDto.setName("newName");

        given(service.updatePerson("5087fb1f-8d57-46e0-9cdb-ad70855f0fc4", personRequestDto))
                .willReturn(dto);

        mvc.perform(put("/persons/5087fb1f-8d57-46e0-9cdb-ad70855f0fc4")
                            .with(httpBasic("admin", "admin"))
                            .accept(APPLICATION_JSON)
                            .contentType(APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(personRequestDto))
                   )

                .andExpect(status().isOk())
                .andExpect(jsonPath("name").isNotEmpty())
                .andExpect(jsonPath("name").value("newName"));
    }

    @Test
    void shouldDeletePerson() throws Exception {
        doNothing().when(service).deletePerson("5087fb1f-8d57-46e0-9cdb-ad70855f0fc4");
        mvc.perform(delete("/persons/5087fb1f-8d57-46e0-9cdb-ad70855f0fc4")
                            .with(httpBasic("admin", "admin"))
                            .accept(APPLICATION_JSON)
                            .contentType(APPLICATION_JSON)
                   )

                .andExpect(status().isNoContent());
    }
}
