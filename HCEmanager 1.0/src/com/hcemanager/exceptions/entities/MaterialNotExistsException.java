package com.hcemanager.exceptions.entities;

import org.springframework.dao.EmptyResultDataAccessException;

/**
 * @author daniel, juan.
 */
public class MaterialNotExistsException extends Exception {
    public MaterialNotExistsException() {
    }

    public MaterialNotExistsException(EmptyResultDataAccessException e) {

    }
}
