package com.app.infrastructure.persistence.entities;

import com.app.domain.user.User;
import com.app.domain.user.enums.Role;
import com.app.infrastructure.persistence.entities.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "users")
public class UserEntity extends BaseEntity {
    private String username;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    public User toUser() {
        return User
                .builder()
                .id(id)
                .username(username)
                .password(password)
                .role(role)
                .build();
    }
}
