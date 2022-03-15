package com.app.infrastructure.controller;

import com.app.application.service.UserService;
import com.app.domain.user.dto.GetUserDto;
import com.app.infrastructure.dto.ResponseData;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/find")
@RequiredArgsConstructor
public class FindUserController {
    private final UserService userService;

    @GetMapping("/username/{username}")
    public ResponseData<GetUserDto> findUserByUsername(@PathVariable String username) {
        return ResponseData.<GetUserDto>builder()
                .data(userService.findByUsername(username))
                .build();
    }

    @GetMapping("/id/{id}")
    public ResponseData<GetUserDto> findUserById(@PathVariable Long id) {
        return ResponseData.<GetUserDto>builder()
                .data(userService.findById(id))
                .build();
    }
}
