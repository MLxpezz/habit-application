package com.habit.management.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ApiSuccessfullyMessageEnum {

    SUCCESS_REGISTER(HttpStatus.CREATED, "El registro se realizo con exito.");

    private final HttpStatus status;
    private final String message;

    ApiSuccessfullyMessageEnum(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
