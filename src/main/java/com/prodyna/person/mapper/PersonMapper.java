package com.prodyna.person.mapper;

import java.util.UUID;

import com.prodyna.person.dto.PersonRequestDto;
import com.prodyna.person.dto.PersonResponseDto;
import com.prodyna.person.model.Person;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PersonMapper {


    @Mapping(target = "personId", source = "person.id", qualifiedByName = "convertUUIDtoString")
    @Mapping(target = "name", source = "person.name")
    PersonResponseDto toPersonResponse(Person person);


    @Mapping(target = "name", source = "personRequestDto.name")
    Person toPerson(PersonRequestDto personRequestDto);

    @Named("convertUUIDtoString")
    static String convertUUIDtoString(UUID id) {
        return String.valueOf(id);
    }
}
