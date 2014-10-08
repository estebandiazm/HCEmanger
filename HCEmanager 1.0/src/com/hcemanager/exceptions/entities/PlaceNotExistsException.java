package com.hcemanager.exceptions.entities;

import org.springframework.dao.EmptyResultDataAccessException;

/**
 * @author daniel, juan.
 */
public class PlaceNotExistsException extends Exception {
    public PlaceNotExistsException() {
    }

    public PlaceNotExistsException(EmptyResultDataAccessException e) {

    }
}
