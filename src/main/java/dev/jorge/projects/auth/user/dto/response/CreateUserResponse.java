package dev.jorge.projects.auth.user.dto.response;

import dev.jorge.projects.auth.user.entity.User;

public record CreateUserResponse(String firstName, String lastName, String email)
{
    public static CreateUserResponse fromEntity(User user) {
        return new CreateUserResponse(
                user.getFirstName(),
                user.getLastName(),
                user.getEmail()
        );
    }
}
