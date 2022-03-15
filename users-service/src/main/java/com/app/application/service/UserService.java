package com.app.application.service;

import com.app.domain.user.User;
import com.app.domain.user.dto.CreateUserDto;
import com.app.domain.user.dto.CreateUserResponseDto;
import com.app.domain.user.dto.GetUserDto;
import com.app.exception.UsersServiceException;
import com.app.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final AppPasswordEncoder appPasswordEncoder;

    public CreateUserResponseDto createUser(CreateUserDto createUserDto) {

        if (createUserDto == null) {
            throw new UsersServiceException("create user dto is null");
        }

        if (userRepository.findByUsername(createUserDto.getUsername()).isPresent()) {
            throw new UsersServiceException("username already exists");
        }

        createUserDto.setPassword(appPasswordEncoder.encode(createUserDto.getPassword()));
        var user = createUserDto.toUser();

        return userRepository
                .save(user)
                .map(User::toCreateUserResponseDto)
                .orElseThrow(() -> new UsersServiceException("Cannot add user to database"));
    }

    public GetUserDto findByUsername(String username) {
        if (username == null) {
            throw new UsersServiceException("Username is null");
        }
        return userRepository
                .findByUsername(username)
                .map(User::toGetUserDto)
                .orElseThrow(() -> new UsersServiceException("Cannot find user by username"));
    }

    public GetUserDto findById(Long id) {
        if (id == null) {
            throw new UsersServiceException("Id is null");
        }
        return userRepository
                .findById(id)
                .map(User::toGetUserDto)
                .orElseThrow(() -> new UsersServiceException("Cannot find user by id"));
    }
}
