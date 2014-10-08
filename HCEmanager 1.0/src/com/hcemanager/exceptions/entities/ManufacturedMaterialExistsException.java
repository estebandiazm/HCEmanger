package com.hcemanager.exceptions.entities;

import org.springframework.dao.DataIntegrityViolationException;

/**
 * @author daniel, juan.
 */
public class ManufacturedMaterialExistsException extends Exception {
    public ManufacturedMaterialExistsException(DataIntegrityViolationException e) {
    }

    public ManufacturedMaterialExistsException() {
    }
}
