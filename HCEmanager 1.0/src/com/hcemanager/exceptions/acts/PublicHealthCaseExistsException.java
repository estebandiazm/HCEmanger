package com.hcemanager.exceptions.acts;

import org.springframework.dao.DataIntegrityViolationException;

/**
 * @author daniel, esteban.
 */
public class PublicHealthCaseExistsException extends Exception {
    /**
     * Constructor with an exception argument
     * @param e
     */
    public PublicHealthCaseExistsException(DataIntegrityViolationException e) {
    }
}
