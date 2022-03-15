package com.app.infrastructure.persistence.repository.impl;

import com.app.domain.user.User;
import com.app.domain.user.repository.UserRepository;
import com.app.infrastructure.persistence.entities.UserEntity;
import com.app.infrastructure.persistence.repository.jpa.JpaUserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final JpaUserEntityRepository jpaUserEntityRepository;

    @Override
    public Optional<User> save(User user) {
        var savedUser = jpaUserEntityRepository.save(user.toEntity());
        return Optional.ofNullable(savedUser.toUser());
    }

    @Override
    public Optional<User> findById(Long id) {
        return jpaUserEntityRepository
                .findById(id)
                .map(UserEntity::toUser);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return jpaUserEntityRepository
                .findByUsername(username)
                .map(UserEntity::toUser);
    }

    @Override
    public Optional<User> deleteById(Long id) {
        return jpaUserEntityRepository
                .findById(id)
                .flatMap(userEntity -> {
                    jpaUserEntityRepository.delete(userEntity);
                    return Optional.ofNullable(userEntity.toUser());
                });
    }

    @Override
    public List<User> findAll() {
        return jpaUserEntityRepository
                .findAll()
                .stream()
                .map(UserEntity::toUser)
                .collect(Collectors.toList());
    }
}
