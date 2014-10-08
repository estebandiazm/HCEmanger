package com.hcemanager.exceptions.roles;

import org.springframework.dao.EmptyResultDataAccessException;

/**
 * @author daniel, juan.
 */
public class PatientNotExistsException extends Exception {
    public PatientNotExistsException() {
    }

    public PatientNotExistsException(EmptyResultDataAccessException e) {

    }
}
