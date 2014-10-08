package com.hcemanager.exceptions.roles;

import org.springframework.dao.DataIntegrityViolationException;

/**
 * @author daniel, juan.
 */
public class LicensedEntityExistsException extends Exception {
    public LicensedEntityExistsException(DataIntegrityViolationException e) {
    }
}
