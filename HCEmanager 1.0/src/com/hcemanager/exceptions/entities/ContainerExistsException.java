package com.hcemanager.exceptions.entities;

import org.springframework.dao.DataIntegrityViolationException;

/**
 * @author daniel, juan.
 */
public class ContainerExistsException extends Exception{
    public ContainerExistsException(DataIntegrityViolationException e) {
    }
}
