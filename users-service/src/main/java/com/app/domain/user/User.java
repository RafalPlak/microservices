package com.app.domain.user;

import com.app.domain.user.dto.CreateUserResponseDto;
import com.app.domain.user.dto.GetUserDto;
import com.app.domain.user.enums.Role;
import com.app.infrastructure.persistence.entities.UserEntity;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class User {
    Long id;
    String username;
    String password;
    Role role;

    public GetUserDto toGetUserDto() {
        return GetUserDto
                .builder()
                .id(id)
                .username(username)
                .password(password)
                .role(role)
                .build();
    }

    public CreateUserResponseDto toCreateUserResponseDto() {
        return CreateUserResponseDto
                .builder()
                .id(id)
                .username(username)
                .build();
    }

    public UserEntity toEntity() {
        return UserEntity
                .builder()
                .id(id)
                .username(username)
                .password(password)
                .role(role)
                .build();
    }
}
