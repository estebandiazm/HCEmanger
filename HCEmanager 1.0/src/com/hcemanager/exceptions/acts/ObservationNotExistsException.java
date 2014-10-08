package com.hcemanager.exceptions.acts;

import org.springframework.dao.EmptyResultDataAccessException;

/**
 * @author daniel, esteban.
 */
public class ObservationNotExistsException extends Exception {
    /**
     * Default constructor
     */
    public ObservationNotExistsException() {
    }

    /**
     * Constructor with an exception argument
     * @param e
     */
    public ObservationNotExistsException(EmptyResultDataAccessException e) {

    }
}
