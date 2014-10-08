package com.hcemanager.exceptions.acts;

import org.springframework.dao.DataIntegrityViolationException;

/**
 * @author daniel, esteban.
 */
public class SupplyExistsException extends Exception {
    public SupplyExistsException(DataIntegrityViolationException e) {
    }
}
