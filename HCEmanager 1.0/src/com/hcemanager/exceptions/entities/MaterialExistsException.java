package com.hcemanager.exceptions.entities;

import org.springframework.dao.DataIntegrityViolationException;

/**
 * @author daniel, juan.
 */
public class MaterialExistsException extends Exception {
    public MaterialExistsException(DataIntegrityViolationException e) {
    }

    public MaterialExistsException() {
    }
}
