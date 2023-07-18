package com.prodyna.person.exception;

import java.time.ZonedDateTime;
import java.util.Collection;

import com.prodyna.person.dto.PersonErrorResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

@Slf4j
@RestControllerAdvice
public class PersonExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<PersonErrorResponseDto> handleNotFound(NotFoundException e) {
        log.error("Person is not found");
        return new ResponseEntity<>(new PersonErrorResponseDto(HttpStatus.NOT_FOUND.value(), e.getMessage(), ZonedDateTime.now()),
                                    HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<PersonErrorResponseDto> handleValidationExceptions(BadRequestException ex) {
        log.error("Bad request", ex);
        return new ResponseEntity<>(
                new PersonErrorResponseDto(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), ZonedDateTime.now()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<PersonErrorResponseDto> handle(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        FieldError fieldError = bindingResult.getFieldError();
        StringBuilder message = new StringBuilder();
        if (fieldError != null) {
            String rejectReason = fieldError.getDefaultMessage();

            message.append(fieldError.getField()).append(" ");

            if (StringUtils.hasText(rejectReason)) {
                message.append(rejectReason);
            } else {
                message.append("must not be empty");
            }
        } else {
            if (!bindingResult.getAllErrors().isEmpty()) {
                Collection<ObjectError> errors = bindingResult.getAllErrors();
                for (ObjectError e : errors) {
                    message.append("Validation in the request object \"")
                            .append(e.getObjectName())
                            .append("\"")
                            .append(" is failed. Value was rejected by [")
                            .append(e.getCode())
                            .append("] constraint validator")
                            .append(" with message \"")
                            .append(e.getDefaultMessage())
                            .append("\"");
                }
            } else {
                message.append("Field validation failed");
            }
        }
        return new ResponseEntity<>(
                new PersonErrorResponseDto(HttpStatus.BAD_REQUEST.value(), message.toString(), ZonedDateTime.now()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<PersonErrorResponseDto> handleAuthenticationException(Exception ex) {
        log.error("Not authorized", ex);
        return new ResponseEntity<>(
                new PersonErrorResponseDto(HttpStatus.UNAUTHORIZED.value(), ex.getMessage(), ZonedDateTime.now()),
                HttpStatus.UNAUTHORIZED);
    }
}
