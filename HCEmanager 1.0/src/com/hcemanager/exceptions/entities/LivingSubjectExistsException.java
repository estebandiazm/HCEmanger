package com.hcemanager.exceptions.entities;

import org.springframework.dao.DataIntegrityViolationException;

/**
 * @author daniel, juan.
 */
public class LivingSubjectExistsException extends Exception {
    public LivingSubjectExistsException(DataIntegrityViolationException e) {
    }

    public LivingSubjectExistsException() {
    }
}
