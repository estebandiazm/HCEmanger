package com.hcemanager.exceptions.acts;

import org.springframework.dao.DataIntegrityViolationException;

/**
 * @author daniel, esteban.
 */
public class WorkingListCannotBeDeletedException extends Exception {
    public WorkingListCannotBeDeletedException(DataIntegrityViolationException e) {
    }
}
