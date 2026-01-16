package de.unibayreuth.se.taskboard.api.dtos;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserDto(
        UUID id,
        @NotBlank String name,
        LocalDateTime createdAt
) { }
