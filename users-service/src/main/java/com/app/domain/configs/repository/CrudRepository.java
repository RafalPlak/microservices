package com.app.domain.configs.repository;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<T, ID> {
    Optional<T> save(T t);
    Optional<T> findById(ID id);
    Optional<T> deleteById(ID id);
    List<T> findAll();
}
