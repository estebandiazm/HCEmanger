package com.hcemanager.exceptions.entities;

import org.springframework.dao.DataIntegrityViolationException;

/**
 * @author daniel, juan.
 */
public class LivingSubjectCannotBeDeletedException extends Exception {
    public LivingSubjectCannotBeDeletedException(DataIntegrityViolationException e) {
    }

    public LivingSubjectCannotBeDeletedException() {
    }
}
