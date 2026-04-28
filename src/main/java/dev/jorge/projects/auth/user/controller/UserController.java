package dev.jorge.projects.auth.user.controller;

import dev.jorge.projects.auth.user.assembler.UserDetailAssembler;
import dev.jorge.projects.auth.user.assembler.UserSummaryAssembler;

import dev.jorge.projects.auth.user.dto.response.GetAllUserResponse;
import dev.jorge.projects.auth.user.dto.response.GetOneUserResponse;
import dev.jorge.projects.auth.common.dto.response.PageResponse;

import dev.jorge.projects.auth.user.entity.User;

import dev.jorge.projects.auth.user.service.UserService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.hateoas.EntityModel;

import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Controller
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;
    private final UserSummaryAssembler summaryAssembler;
    private final UserDetailAssembler detailAssembler;


    public UserController(UserService userService, UserSummaryAssembler summaryAssembler, UserDetailAssembler detailAssembler)
    {
        this.userService = userService;
        this.summaryAssembler = summaryAssembler;
        this.detailAssembler = detailAssembler;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<EntityModel<GetOneUserResponse>> getOneUser(@PathVariable UUID userId) {
        User user = userService.findById(userId);
        return ResponseEntity.ok(detailAssembler.toModel(user));
    }

    @GetMapping
    public ResponseEntity<PageResponse<EntityModel<GetAllUserResponse>>> getAllUsers(Pageable pageable) {
        Page<User> users = userService.findAll(pageable);
        return ResponseEntity.ok(PageResponse.fromPage(users.map(summaryAssembler::toModel)));
    }
}
