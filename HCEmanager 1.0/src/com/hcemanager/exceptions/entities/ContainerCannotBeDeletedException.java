package com.hcemanager.exceptions.entities;

import org.springframework.dao.DataIntegrityViolationException;

/**
 * @author daniel, juan.
 */
public class ContainerCannotBeDeletedException extends Exception {
    public ContainerCannotBeDeletedException(DataIntegrityViolationException e) {
    }
}
