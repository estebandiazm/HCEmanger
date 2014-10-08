package com.hcemanager.exceptions.acts;

import org.springframework.dao.EmptyResultDataAccessException;

/**
 * @author daniel, esteban.
 */
public class DeviceTaskNotExistsException extends Exception {

    /**
     * Default constructor
     */
    public DeviceTaskNotExistsException() {
    }

    /**
     * Constructor with an exception argument
     * @param e
     */
    public DeviceTaskNotExistsException(EmptyResultDataAccessException e) {

    }
}
