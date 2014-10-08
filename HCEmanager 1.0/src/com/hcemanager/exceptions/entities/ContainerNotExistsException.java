package com.hcemanager.exceptions.entities;

import org.springframework.dao.EmptyResultDataAccessException;

/**
 * @author daniel, juan.
 */
public class ContainerNotExistsException extends Exception {
    public ContainerNotExistsException() {
    }

    public ContainerNotExistsException(EmptyResultDataAccessException e) {

    }
}
