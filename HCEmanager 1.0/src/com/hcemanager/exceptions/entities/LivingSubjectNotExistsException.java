package com.hcemanager.exceptions.entities;

import org.springframework.dao.EmptyResultDataAccessException;

/**
 * @author daniel, juan.
 */
public class LivingSubjectNotExistsException extends Exception {
    public LivingSubjectNotExistsException(EmptyResultDataAccessException e) {
    }

    public LivingSubjectNotExistsException() {
    }
}
