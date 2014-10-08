package com.hcemanager.exceptions.acts;

import org.springframework.dao.DataIntegrityViolationException;

/**
 * @author daniel, esteban.
 */
public class ObservationExistsException extends Exception{

    /**
     * Constructor with an exception argument
     * @param e
     */
    public ObservationExistsException(DataIntegrityViolationException e) {
    }
}
