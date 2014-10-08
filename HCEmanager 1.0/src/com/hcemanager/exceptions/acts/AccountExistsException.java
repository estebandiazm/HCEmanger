package com.hcemanager.exceptions.acts;

import org.springframework.dao.DataIntegrityViolationException;

/**
 * @author daniel, esteban.
 */
public class AccountExistsException extends Exception {
    public AccountExistsException(DataIntegrityViolationException e) {

    }

    public void printStackTrace(String s) {
        printStackTrace(s);
    }
}
