package com.hcemanager.exceptions.roles;

import org.springframework.dao.DataIntegrityViolationException;

/**
 * @author daniel, juan.
 */
public class PatientExistsException extends Exception {
    public PatientExistsException(DataIntegrityViolationException e) {
    }
}
