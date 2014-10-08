package com.hcemanager.exceptions.acts;

import org.springframework.dao.DataIntegrityViolationException;

/**
 * @author daniel, esteban.
 */
public class PatientEncounterExistsException extends Exception{
    public PatientEncounterExistsException(DataIntegrityViolationException e) {
    }
}
