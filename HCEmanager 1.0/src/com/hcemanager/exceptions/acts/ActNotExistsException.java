package com.hcemanager.exceptions.acts;

import org.springframework.dao.EmptyResultDataAccessException;

/**
 * @author daniel, esteban.
 */
public class ActNotExistsException extends Exception {

    /**
     * Default constructor
     */
    public ActNotExistsException() {
    }

    /**
     * Default constructor with an exception argument
     * @param e
     */
    public ActNotExistsException(EmptyResultDataAccessException e) {

    }
}
