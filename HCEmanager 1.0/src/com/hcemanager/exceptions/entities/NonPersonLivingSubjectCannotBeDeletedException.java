package com.hcemanager.exceptions.entities;

import org.springframework.dao.DataIntegrityViolationException;

/**
 * @author daniel, juan.
 */
public class NonPersonLivingSubjectCannotBeDeletedException extends Exception {
    public NonPersonLivingSubjectCannotBeDeletedException(DataIntegrityViolationException e) {
    }

    public NonPersonLivingSubjectCannotBeDeletedException() {
    }
}
