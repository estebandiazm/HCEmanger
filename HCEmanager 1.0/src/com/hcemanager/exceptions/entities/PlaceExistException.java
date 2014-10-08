package com.hcemanager.exceptions.entities;

import org.springframework.dao.DataIntegrityViolationException;

/**
 * @author daniel, juan.
 */
public class PlaceExistException extends Exception {
    public PlaceExistException(DataIntegrityViolationException e) {
    }
}
