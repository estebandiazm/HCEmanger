package com.hcemanager.exceptions.entities;

import org.springframework.dao.DataIntegrityViolationException;

/**
 * @author daniel, juan.
 */
public class OrganizationCannotBeDeletedException extends Exception {
    public OrganizationCannotBeDeletedException(DataIntegrityViolationException e) {
    }
}
