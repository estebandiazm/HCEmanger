package com.hcemanager.exceptions.acts;

import org.springframework.dao.DataIntegrityViolationException;

/**
 * @author daniel, esteban.
 */
public class ObservationCannotBeDeleteException extends Exception {
    /**
     * Constructor with an esception argument
     * @param e
     */
    public ObservationCannotBeDeleteException(DataIntegrityViolationException e) {
    }
}
