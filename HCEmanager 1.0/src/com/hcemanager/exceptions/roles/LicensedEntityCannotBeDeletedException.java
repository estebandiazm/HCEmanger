package com.hcemanager.exceptions.roles;

import org.springframework.dao.DataIntegrityViolationException;

/**
 * @author daniel, juan.
 */
public class LicensedEntityCannotBeDeletedException extends Exception {
    public LicensedEntityCannotBeDeletedException(DataIntegrityViolationException e) {
    }
}
