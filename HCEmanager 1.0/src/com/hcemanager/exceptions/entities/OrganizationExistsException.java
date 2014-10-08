package com.hcemanager.exceptions.entities;

import org.springframework.dao.DataIntegrityViolationException;

/**
 * @author daniel, juan.
 */
public class OrganizationExistsException extends Exception {
    public OrganizationExistsException() {
    }

    public OrganizationExistsException(DataIntegrityViolationException e) {

    }
}
