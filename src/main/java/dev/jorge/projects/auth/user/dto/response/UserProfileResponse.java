package dev.jorge.projects.auth.user.dto.response;

import dev.jorge.projects.auth.user.entity.User;
import dev.jorge.projects.auth.user.enums.Role;

import java.util.Set;
import java.util.UUID;

public record UserProfileResponse(
        UUID id,
        String firstName,
        String lastName,
        String email,
        Set<Role> roles
)
{
    public static UserProfileResponse fromEntity(User user) {
        return new UserProfileResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getRoles()
        );
    }
}
