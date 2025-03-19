package com.pichincha.exam.users.infrastructure.exception;

public class ClientNotFound extends RuntimeException {

    public ClientNotFound(String message) {
        super(message);
    }
}
