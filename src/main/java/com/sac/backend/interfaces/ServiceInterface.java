package com.sac.backend.interfaces;


import java.util.List;
import java.util.Optional;

public interface ServiceInterface<T> {
    T create(T obj);
    Optional<T> findById(Long id);
    List<T> findAll();
    boolean update(T obj);
    boolean delete(Long id);
}