package com.hcemanager.exceptions.roles;

import org.springframework.dao.DataIntegrityViolationException;

/**
 * @author daniel, juan.
 */
public class PatientCannotBeDeleted extends Exception {
    public PatientCannotBeDeleted(DataIntegrityViolationException e) {
    }
}
