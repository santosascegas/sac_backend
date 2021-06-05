package com.sac.backend.interfaces;

import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * @author Maurício Freire
 * Date 04/06/2021 at 23:26
 * Created on IntelliJ IDEA
 */

public interface Control<K> {
    ResponseEntity<?> delete(Long id);
    ResponseEntity<List<K>> getAll();
    ResponseEntity<?> getById(Long id);
    ResponseEntity<K> post(K obj);
    ResponseEntity<?> put(K obj);
}
