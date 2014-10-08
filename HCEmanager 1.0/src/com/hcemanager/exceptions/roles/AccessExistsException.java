package com.hcemanager.exceptions.roles;

import org.springframework.dao.DataIntegrityViolationException;

/**
 * @author daniel, juan.
 */
public class AccessExistsException extends Exception {
    public AccessExistsException(DataIntegrityViolationException e) {
    }
}
