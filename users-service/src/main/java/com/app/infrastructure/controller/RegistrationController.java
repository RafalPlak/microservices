package com.app.infrastructure.controller;

import com.app.domain.user.dto.CreateUserDto;
import com.app.domain.user.dto.CreateUserResponseDto;
import com.app.infrastructure.dto.ResponseData;
import com.app.application.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/security")
public class RegistrationController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseData<CreateUserResponseDto> createUser(@RequestBody CreateUserDto createUserDto) {
        return ResponseData
                .<CreateUserResponseDto>builder()
                .data(userService.createUser(createUserDto))
                .build();
    }
}
