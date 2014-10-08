package com.hcemanager.exceptions.acts;

import org.springframework.dao.DataIntegrityViolationException;

/**
 * @author daniel, esteban.
 */
public class ActCannotBeDeleted extends Exception {
    public ActCannotBeDeleted(DataIntegrityViolationException e) {
    }
}
