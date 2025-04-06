package com.pichincha.exam.users.infrastructure.input.adapter.rest.error;

import com.pichincha.exam.users.domain.Error;
import com.pichincha.exam.users.infrastructure.exception.ClientNotFound;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ServerWebInputException;

import static com.pichincha.exam.users.infrastructure.constants.ErrorConstants.DUPLICATED;
import static com.pichincha.exam.users.infrastructure.constants.ErrorConstants.ERROR_REQUEST;

@Slf4j
@RestControllerAdvice
public class ControllerHandler {

    @ExceptionHandler(ClientNotFound.class)
    public ResponseEntity<Object> handleFailure(Exception exception) {
        log.error(exception.toString());
        return buildResponseEntity(new Error(exception.getMessage(), HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<Object> handleFailureForSQL(Exception exception) {
        log.error(exception.toString());
        return buildResponseEntity(new Error(DUPLICATED, HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler({WebExchangeBindException.class, ServerWebInputException.class})
    public ResponseEntity<Object> handleFailureForPattern(Exception exception) {
        log.error(exception.toString());
        return buildResponseEntity(new Error(ERROR_REQUEST, HttpStatus.BAD_REQUEST));
    }

    private ResponseEntity<Object> buildResponseEntity(Error error) {
        return new ResponseEntity<>(error, error.getStatusCode());
    }
}
