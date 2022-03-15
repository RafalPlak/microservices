package com.app.domain.user.repository;

import com.app.domain.configs.repository.CrudRepository;
import com.app.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
