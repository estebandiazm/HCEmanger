package com.hcemanager.exceptions.roles;

import org.springframework.dao.EmptyResultDataAccessException;

/**
 * @author daniel, juan.
 */
public class AccessNotExistsException extends Exception {
    public AccessNotExistsException() {
    }

    public AccessNotExistsException(EmptyResultDataAccessException e) {

    }
}
