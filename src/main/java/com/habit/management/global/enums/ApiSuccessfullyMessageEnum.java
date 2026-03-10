package com.habit.management.global.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ApiSuccessfullyMessageEnum {

    SUCCESS_REGISTER(HttpStatus.CREATED, "El registro se realizo con exito."),
    SUCCESS_LOGIN(HttpStatus.OK, "EL usuario se logueo con exito.");

    private final HttpStatus status;
    private final String message;

    ApiSuccessfullyMessageEnum(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
