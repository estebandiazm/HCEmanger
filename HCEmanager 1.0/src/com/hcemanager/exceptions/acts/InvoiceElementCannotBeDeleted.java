package com.hcemanager.exceptions.acts;

import org.springframework.dao.DataIntegrityViolationException;

/**
 * @author daniel, esteban.
 */
public class InvoiceElementCannotBeDeleted extends Exception {

    /**
     * Default constructor
     */
    public InvoiceElementCannotBeDeleted() {
    }

    /**
     * Constructor with an exception argument
     * @param e
     */
    public InvoiceElementCannotBeDeleted(DataIntegrityViolationException e) {

    }
}
