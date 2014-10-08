package com.hcemanager.exceptions.acts;

import org.springframework.dao.DataIntegrityViolationException;

/**
 * @author daniel, esteban.
 */
public class WorkingListExistsException extends Exception {
    public WorkingListExistsException(DataIntegrityViolationException e) {
    }
}
