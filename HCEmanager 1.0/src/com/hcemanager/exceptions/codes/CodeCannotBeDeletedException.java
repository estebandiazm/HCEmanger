package com.hcemanager.exceptions.codes;

import org.springframework.dao.DataIntegrityViolationException;

/**
 * @author daniel, juan.
 */
public class CodeCannotBeDeletedException extends Throwable {
    public CodeCannotBeDeletedException(DataIntegrityViolationException e) {
    }
}
