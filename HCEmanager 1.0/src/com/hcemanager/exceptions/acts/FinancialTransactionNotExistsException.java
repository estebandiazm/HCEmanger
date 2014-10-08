package com.hcemanager.exceptions.acts;

import org.springframework.dao.EmptyResultDataAccessException;

/**
 * @author daniel, esteban.
 */
public class FinancialTransactionNotExistsException extends Exception {

    /**
     * Default constructor
     */
    public FinancialTransactionNotExistsException() {
    }

    /**
     *
     * @param e
     */
    public FinancialTransactionNotExistsException(EmptyResultDataAccessException e) {

    }
}
