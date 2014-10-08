package com.hcemanager.exceptions.acts;

import org.springframework.dao.DataIntegrityViolationException;

/**
 * @author daniel, esteban.
 */
public class ProcedureCannotBeDeletedException extends Exception {
    /**
     * Constructor with an exception argument
     * @param e
     */
    public ProcedureCannotBeDeletedException(DataIntegrityViolationException e) {
    }
}
