package ru.flamexander.spring.data.jdbc.demo.exceptions;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.flamexander.spring.data.jdbc.demo.dtos.ErrorDto;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDto> catchResourceNotFoundException(ResourceNotFoundException e) {
        return new ResponseEntity<>(new ErrorDto("RESOURCE_NOT_FOUND",
                e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorDto> catchIllegalArgumentException(IllegalArgumentException e) {
        return new ResponseEntity<>(new ErrorDto("ILLEGAL_ARGUMENT",
                e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorDto> catchConstraintValidationException(ConstraintViolationException e){
        return new ResponseEntity<>(new ErrorDto("CONSTRAINT_VALIDATION_FAILED",
                e.getMessage()), HttpStatus.BAD_REQUEST);
    }

}
