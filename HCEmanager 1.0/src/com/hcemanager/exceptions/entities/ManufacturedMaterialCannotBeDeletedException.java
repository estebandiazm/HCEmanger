package com.hcemanager.exceptions.entities;

import org.springframework.dao.DataIntegrityViolationException;

/**
 * @author daniel, juan.
 */
public class ManufacturedMaterialCannotBeDeletedException extends Exception {
    public ManufacturedMaterialCannotBeDeletedException(DataIntegrityViolationException e) {
    }

    public ManufacturedMaterialCannotBeDeletedException() {
    }
}
