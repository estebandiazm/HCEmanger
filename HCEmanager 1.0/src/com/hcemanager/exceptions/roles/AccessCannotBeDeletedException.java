package com.hcemanager.exceptions.roles;

import org.springframework.dao.DataIntegrityViolationException;

/**
 * @author daniel, juan.
 */
public class AccessCannotBeDeletedException extends Exception {
    public AccessCannotBeDeletedException(DataIntegrityViolationException e) {

    }
}
