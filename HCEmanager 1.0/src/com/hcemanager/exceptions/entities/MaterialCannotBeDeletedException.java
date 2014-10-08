package com.hcemanager.exceptions.entities;

import org.springframework.dao.DataIntegrityViolationException;

/**
 * @author daniel, juan.
 */
public class MaterialCannotBeDeletedException extends Exception {
    public MaterialCannotBeDeletedException(DataIntegrityViolationException e) {
    }
}
