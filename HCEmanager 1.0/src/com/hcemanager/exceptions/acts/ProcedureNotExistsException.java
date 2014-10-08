package com.hcemanager.exceptions.acts;

import org.springframework.dao.EmptyResultDataAccessException;

/**
 * @author daniel, esteban.
 */
public class ProcedureNotExistsException extends Exception{
    /**
     * Default constructor
     */
    public ProcedureNotExistsException() {
    }

    /**
     * Constructor with an exception argument
     * @param e
     */
    public ProcedureNotExistsException(EmptyResultDataAccessException e) {

    }
}
