package com.hcemanager.exceptions.acts;

import org.springframework.dao.DataIntegrityViolationException;

/**
 * @author daniel, esteban.
 */
public class DiagnosticImageExistsException extends Exception {

    /**
     * Constructor with an exception argument
     * @param e
     */
    public DiagnosticImageExistsException(DataIntegrityViolationException e) {
    }
}
