package com.hcemanager.exceptions.entities;

import org.springframework.dao.EmptyResultDataAccessException;

/**
 * @author daniel, juan.
 */
public class OrganizationNotExistsException extends Exception {
    public OrganizationNotExistsException() {
    }

    public OrganizationNotExistsException(EmptyResultDataAccessException e) {

    }
}
