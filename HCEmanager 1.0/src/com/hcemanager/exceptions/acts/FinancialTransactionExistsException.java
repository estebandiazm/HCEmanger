package com.hcemanager.exceptions.acts;

import org.springframework.dao.DataIntegrityViolationException;

/**
 * @author daniel, esteban.
 */
public class FinancialTransactionExistsException extends Exception {

    /**
     * Default constructor
     * @param e
     */
    public FinancialTransactionExistsException(DataIntegrityViolationException e) {
    }
}
