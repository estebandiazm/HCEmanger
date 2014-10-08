package com.hcemanager.exceptions.acts;

import org.springframework.dao.EmptyResultDataAccessException;

/**
 * @author daniel, esteban.
 */
public class SupplyNotExistsException extends Exception {
    public SupplyNotExistsException() {
    }

    public SupplyNotExistsException(EmptyResultDataAccessException e) {

    }
}
