package de.unibayreuth.se.taskboard.api.dtos;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO for users.
 *
 * id / createdAt are typically set by the backend when the user is created.
 * For creating a user via POST, only "name" is required.
 */
public record UserDto(
        UUID id,
        @NotBlank String name,
        LocalDateTime createdAt
) { }
