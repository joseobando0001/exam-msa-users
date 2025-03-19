package com.pichincha.exam.users.infrastructure.input.adapter.rest.error;

import com.pichincha.exam.users.domain.Error;
import com.pichincha.exam.users.infrastructure.exception.ClientNotFound;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ControllerHandler {

    @ExceptionHandler(ClientNotFound.class)
    public ResponseEntity<Object> handleFailure(Exception exception) {
        log.error(exception.toString());
        return buildResponseEntity(new Error(exception.getMessage(), HttpStatus.BAD_REQUEST));
    }

    private ResponseEntity<Object> buildResponseEntity(Error error) {
        return new ResponseEntity<>(error, error.getStatusCode());
    }
}
