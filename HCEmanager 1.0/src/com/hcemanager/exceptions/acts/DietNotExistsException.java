package com.hcemanager.exceptions.acts;

import org.springframework.dao.EmptyResultDataAccessException;

/**
 * @author daniel, esteban.
 */
public class DietNotExistsException extends Exception {

    /**
     * Default constructor
     */
    public DietNotExistsException() {
    }

    /**
     * Constructor with an exception argument
     * @param e
     */
    public DietNotExistsException(EmptyResultDataAccessException e) {

    }
}
