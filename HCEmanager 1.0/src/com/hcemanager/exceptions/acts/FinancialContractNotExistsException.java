package com.hcemanager.exceptions.acts;

import org.springframework.dao.EmptyResultDataAccessException;

/**
 * @author daniel, esteban.
 */
public class FinancialContractNotExistsException extends Exception {
    /**
     * Default constructor
     */
    public FinancialContractNotExistsException() {
    }

    /**
     * Constructor with an exception argument
     * @param e
     */
    public FinancialContractNotExistsException(EmptyResultDataAccessException e) {

    }
}
