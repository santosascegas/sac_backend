package com.sac.backend.interfaces;

import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.List;

/**
 * @author Maurício Freire
 * Date 03/06/2021 at 18:07
 * Created on IntelliJ IDEA
 */

public interface InterfaceControle<T> {
//    ResponseEntity<?> delete();
    ResponseEntity<List<?>> findAll();
    ResponseEntity<?> getByData(Date date);
    ResponseEntity<T> post(T t);
    ResponseEntity<?> put(T t);
}
