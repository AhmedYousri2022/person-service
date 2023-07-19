package com.prodyna.person.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class PersonRequestDto {

    @NotNull
    @Size(min = 3, max = 20, message = "length should be between [3,20] characters")
    private String name;
}
