package com.hcemanager.exceptions.entities;

import org.springframework.dao.EmptyResultDataAccessException;

/**
 * @author daniel, juan.
 */
public class EntityNotExistsException extends Exception {
    public EntityNotExistsException() {
    }

    public EntityNotExistsException(EmptyResultDataAccessException e) {

    }
}
