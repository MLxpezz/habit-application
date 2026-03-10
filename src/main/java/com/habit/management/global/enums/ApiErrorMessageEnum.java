package com.habit.management.global.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ApiErrorMessageEnum {

    ROLE_NOT_FOUND(HttpStatus.NOT_FOUND, "El rol no fue encontrado o no existe."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "El usuario no fue encontrado o no existe."),
    EMAIL_ALREADY_EXISTS(HttpStatus.CONFLICT, "El email ya esta registrado.");

    private final HttpStatus status;
    private final String message;

    ApiErrorMessageEnum(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
