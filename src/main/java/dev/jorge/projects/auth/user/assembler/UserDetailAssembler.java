package dev.jorge.projects.auth.user.assembler;

import dev.jorge.projects.auth.user.controller.UserController;
import dev.jorge.projects.auth.user.dto.response.GetOneUserResponse;
import dev.jorge.projects.auth.user.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserDetailAssembler {

    public EntityModel<GetOneUserResponse> toModel(User user) {
        GetOneUserResponse dto = new GetOneUserResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getRoles(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );

        return EntityModel.of(dto,
                linkTo(methodOn(UserController.class)
                        .getOneUser(user.getId()))
                        .withSelfRel(),

                linkTo(methodOn(UserController.class)
                        .getAllUsers(Pageable.unpaged()))
                        .withRel("users"));
    }
}