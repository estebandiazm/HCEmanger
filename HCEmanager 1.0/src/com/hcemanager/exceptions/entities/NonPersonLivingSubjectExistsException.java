package com.hcemanager.exceptions.entities;

import org.springframework.dao.DataIntegrityViolationException;

/**
 * @author daniel, juan.
 */
public class NonPersonLivingSubjectExistsException extends Exception {
    public NonPersonLivingSubjectExistsException() {
    }

    public NonPersonLivingSubjectExistsException(DataIntegrityViolationException e) {

    }
}