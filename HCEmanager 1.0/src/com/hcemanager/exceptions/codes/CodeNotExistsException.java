package com.hcemanager.exceptions.codes;

import org.springframework.dao.EmptyResultDataAccessException;

/**
 * @author daniel, juan.
 */
public class CodeNotExistsException extends Exception {
    public CodeNotExistsException() {
    }

    public CodeNotExistsException(EmptyResultDataAccessException e) {

    }
}
