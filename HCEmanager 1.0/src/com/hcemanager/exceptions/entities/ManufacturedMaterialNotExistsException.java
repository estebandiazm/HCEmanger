package com.hcemanager.exceptions.entities;

import org.springframework.dao.EmptyResultDataAccessException;

/**
 * @author daniel, juan.
 */
public class ManufacturedMaterialNotExistsException extends Exception {
    public ManufacturedMaterialNotExistsException() {
    }

    public ManufacturedMaterialNotExistsException(EmptyResultDataAccessException e) {

    }
}
