package com.hcemanager.exceptions.acts;

import org.springframework.dao.EmptyResultDataAccessException;

/**
 * @author daniel, esteban.
 */
public class PatientEncounterNotExistsException extends Exception {
    /**
     * Default constructor
     */
    public PatientEncounterNotExistsException() {
    }

    /**
     * Constructor with an exception argument
     * @param e
     */
    public PatientEncounterNotExistsException(EmptyResultDataAccessException e) {

    }
}
