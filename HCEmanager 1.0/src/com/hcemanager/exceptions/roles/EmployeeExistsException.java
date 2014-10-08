package com.hcemanager.exceptions.roles;

import org.springframework.dao.DataIntegrityViolationException;

/**
 * @author daniel, juan.
 */
public class EmployeeExistsException extends Exception {
    public EmployeeExistsException(DataIntegrityViolationException e) {
    }
}
