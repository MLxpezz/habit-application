package com.habit.management.model.dto;

import lombok.Builder;

@Builder
public record LoginDto(
        String email,
        String password
) {
}
