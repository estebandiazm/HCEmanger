package com.hcemanager.exceptions.entities;

import org.springframework.dao.DataIntegrityViolationException;

/**
 * @author daniel, juan.
 */
public class PersonExistException extends Exception {
    public PersonExistException() {
    }

    public PersonExistException(DataIntegrityViolationException e) {

    }
}
