package com.prodyna.person.dto;

import java.time.ZonedDateTime;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PersonErrorResponseDto {

    private final int status;

    private final String message;

    private final ZonedDateTime zonedDateTime;
}
