package com.hcemanager.exceptions.acts;

import org.springframework.dao.EmptyResultDataAccessException;

/**
 * @author daniel, esteban.
 */
public class AccountNotExistsException extends Exception {

    /**
     * Default constructor
     */
    public AccountNotExistsException() {
    }

    /**
     * Constrector with an exception argument
     * @param e
     */
    public AccountNotExistsException(EmptyResultDataAccessException e) {

    }
}
