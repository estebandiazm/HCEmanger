package com.hcemanager.exceptions.entities;

import org.springframework.dao.EmptyResultDataAccessException;

/**
 * @author daniel, juan.
 */
public class PersonNotExistException extends Exception {
    public PersonNotExistException() {
    }

    public PersonNotExistException(EmptyResultDataAccessException e) {

    }
}
