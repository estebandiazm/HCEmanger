package com.hcemanager.exceptions.roles;

import org.springframework.dao.DataIntegrityViolationException;

/**
 * @author daniel, juan.
 */
public class RoleExistsException extends Exception {
    public RoleExistsException(DataIntegrityViolationException e) {
    }
}
