package com.hcemanager.exceptions.entities;

import org.springframework.dao.DataIntegrityViolationException;

/**
 * @author daniel, juan.
 */
public class EntityCannotBeDeletedException extends Exception {
    public EntityCannotBeDeletedException(DataIntegrityViolationException e) {
    }
}
