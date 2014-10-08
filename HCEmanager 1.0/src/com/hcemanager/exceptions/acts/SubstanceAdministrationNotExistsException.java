package com.hcemanager.exceptions.acts;

import org.springframework.dao.EmptyResultDataAccessException;

/**
 * @author daniel, esteban.
 */
public class SubstanceAdministrationNotExistsException extends Exception {
    public SubstanceAdministrationNotExistsException() {
    }

    public SubstanceAdministrationNotExistsException(EmptyResultDataAccessException e) {

    }
}
