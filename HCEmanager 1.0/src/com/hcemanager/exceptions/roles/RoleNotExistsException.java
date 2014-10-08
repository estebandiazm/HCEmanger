package com.hcemanager.exceptions.roles;

import org.springframework.dao.EmptyResultDataAccessException;

/**
 * @author daniel, juan.
 */
public class RoleNotExistsException extends Exception {
    public RoleNotExistsException(EmptyResultDataAccessException e) {

    }

    public RoleNotExistsException() {

    }
}
