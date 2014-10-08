package com.hcemanager.exceptions.acts;

import org.springframework.dao.DataIntegrityViolationException;

/**
 * @author daniel, esteban.
 */
public class SupplyCannotBeDeletedException extends Exception {
    public SupplyCannotBeDeletedException(DataIntegrityViolationException e) {
    }
}
