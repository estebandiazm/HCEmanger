package com.hcemanager.exceptions.acts;

import org.springframework.dao.EmptyResultDataAccessException;

/**
 * @author daniel, esteban.
 */
public class InvoiceElementNotExistsException extends Exception {
    /**
     * Default constructor
     */
    public InvoiceElementNotExistsException() {
    }

    /**
     * COnstructor with an exception argument
     * @param e
     */
    public InvoiceElementNotExistsException(EmptyResultDataAccessException e) {

    }
}
