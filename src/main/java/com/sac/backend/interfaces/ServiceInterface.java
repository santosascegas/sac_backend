package com.sac.backend.interfaces;

import java.util.List;

public interface ServiceInterface<T> {
    T create(T obj);
    T findById(Long id);
    List<T> findAll();
    boolean update(T obj);
    boolean delete(Long id);
}
