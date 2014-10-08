package com.hcemanager.exceptions.roles;

import org.springframework.dao.EmptyResultDataAccessException;

/**
 * @author daniel, juan.
 */
public class EmployeeNotExistsException extends Exception {
    public EmployeeNotExistsException() {
    }

    public EmployeeNotExistsException(EmptyResultDataAccessException e) {

    }
}
