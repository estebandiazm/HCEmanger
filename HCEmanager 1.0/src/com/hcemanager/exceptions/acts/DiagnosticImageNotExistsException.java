package com.hcemanager.exceptions.acts;

import org.springframework.dao.EmptyResultDataAccessException;

/**
 * @author daniel, esteban.
 */
public class DiagnosticImageNotExistsException extends Exception
{
    /**
     * Default constructor
     */
    public DiagnosticImageNotExistsException() {
    }

    /**
     * Cosntructor with an exception argument
     * @param e
     */
    public DiagnosticImageNotExistsException(EmptyResultDataAccessException e) {

    }
}
