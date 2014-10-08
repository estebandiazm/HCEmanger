package com.hcemanager.exceptions.entities;

import org.springframework.dao.EmptyResultDataAccessException;

/**
 * @author daniel, juan.
 */
public class NonPersonLivingSubjectNotExistsException extends Exception {
    public NonPersonLivingSubjectNotExistsException() {
    }

    public NonPersonLivingSubjectNotExistsException(EmptyResultDataAccessException e) {

    }
}
