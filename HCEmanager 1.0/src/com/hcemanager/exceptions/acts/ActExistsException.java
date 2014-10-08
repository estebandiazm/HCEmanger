package com.hcemanager.exceptions.acts;

import org.springframework.dao.DataIntegrityViolationException;

/**
 * @author daniel, esteban.
 */
public class ActExistsException extends Exception {
    public ActExistsException(DataIntegrityViolationException e) {
    }

    public void printStackTrace(String s) {
        printStackTrace(s);
    }
}
