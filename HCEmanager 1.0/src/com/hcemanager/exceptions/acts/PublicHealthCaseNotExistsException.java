package com.hcemanager.exceptions.acts;

import org.springframework.dao.EmptyResultDataAccessException;

/**
 * @author daniel, esteban.
 */
public class PublicHealthCaseNotExistsException extends Exception {

    /**
     * Default cosntructor
     */
    public PublicHealthCaseNotExistsException() {
    }

    /**
     * Constructor with an exception argument
     * @param e
     */
    public PublicHealthCaseNotExistsException(EmptyResultDataAccessException e) {

    }
}
