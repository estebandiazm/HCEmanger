package com.hcemanager.exceptions.acts;

import org.springframework.dao.EmptyResultDataAccessException;

/**
 * @author daniel, esteban.
 */
public class WorkingListNotExistsException extends Exception {
    public WorkingListNotExistsException() {
    }

    public WorkingListNotExistsException(EmptyResultDataAccessException e) {

    }
}
