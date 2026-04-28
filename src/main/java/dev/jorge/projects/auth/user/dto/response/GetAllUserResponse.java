package dev.jorge.projects.auth.user.dto.response;

import dev.jorge.projects.auth.user.enums.Role;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public record GetAllUserResponse (
        UUID id,
        String firstName,
        String lastName,
        String email,
        Set<Role> roles,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
){}
