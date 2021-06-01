package com.sac.backend.exception;

/**
 * @author Maurício Freire
 * Date 01/06/2021 at 00:56
 * Created on IntelliJ IDEA
 */

public class AuthorizedException extends RuntimeException {

    public AuthorizedException(String s) {
        super(s);
    }

    public AuthorizedException(String s, Throwable t) {
        super(s, t);
    }
}
