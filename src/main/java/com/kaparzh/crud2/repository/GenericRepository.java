package com.kaparzh.crud2.repository;

import java.util.List;

public interface GenericRepository<T, ID> {

    T save(T value);

    T update(T value);

    void deleteById(ID id);

    T getById(ID id);

    List<T> getAll();
}
