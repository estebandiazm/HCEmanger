package com.hcemanager.exceptions.roles;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;

/**
 * @author daniel, juan.
 */
public class LicensedEntityNotExistsException extends Exception {
    public LicensedEntityNotExistsException() {
    }

    public LicensedEntityNotExistsException(EmptyResultDataAccessException e) {
    }
}
