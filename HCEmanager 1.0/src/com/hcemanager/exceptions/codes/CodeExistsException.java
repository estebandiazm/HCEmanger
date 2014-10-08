package com.hcemanager.exceptions.codes;

import org.springframework.dao.DataIntegrityViolationException;

/**
 * @author daniel, juan.
 */
public class CodeExistsException extends Throwable {
    public CodeExistsException(DataIntegrityViolationException e) {
    }
}
