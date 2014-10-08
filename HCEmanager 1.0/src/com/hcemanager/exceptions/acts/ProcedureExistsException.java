package com.hcemanager.exceptions.acts;

import org.springframework.dao.DataIntegrityViolationException;

/**
 * @author daniel, esteban.
 */
public class ProcedureExistsException extends Exception {
    /**
     * COnstructor with an exception argument
     * @param e
     */
    public ProcedureExistsException(DataIntegrityViolationException e) {
    }
}
