package com.habit.management.model.dto;

import lombok.Builder;

@Builder
public record UserDto(
        String email,
        String password

) {
}
