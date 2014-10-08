package com.hcemanager.exceptions.entities;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;

/**
 * @author daniel, juan.
 */
public class EntityExistsException extends Exception{
    public EntityExistsException(DataIntegrityViolationException e) {
    }

    public EntityExistsException(EmptyResultDataAccessException e) {

    }
}
