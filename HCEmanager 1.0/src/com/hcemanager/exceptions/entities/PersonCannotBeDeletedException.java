package com.hcemanager.exceptions.entities;

import org.springframework.dao.DataIntegrityViolationException;

/**
 * @author daniel, juan.
 */
public class PersonCannotBeDeletedException extends Exception {
    public PersonCannotBeDeletedException(DataIntegrityViolationException e) {
    }
}
