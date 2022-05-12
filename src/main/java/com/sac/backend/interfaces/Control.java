package com.sac.backend.interfaces;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Control<K> {
    ResponseEntity<List<K>> getAll();
    ResponseEntity<?> getById(Long id);
    ResponseEntity<K> post(K obj);
    ResponseEntity<?> put(K obj);
    ResponseEntity<?> delete(Long id);
}