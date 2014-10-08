package com.hcemanager.exceptions.entities;

import org.springframework.dao.DataIntegrityViolationException;

/**
 * @author daniel, juan.
 */
public class PlaceCannnoyBeDeletedException extends Exception {
    public PlaceCannnoyBeDeletedException() {
    }

    public PlaceCannnoyBeDeletedException(DataIntegrityViolationException e) {

    }
}
