package com.hcemanager.exceptions.entities;

import org.springframework.dao.EmptyResultDataAccessException;

/**
 * @author daniel, juan.
 */
public class DeviceNotExistsException extends Exception {

    public DeviceNotExistsException(EmptyResultDataAccessException e) {

    }

    public DeviceNotExistsException() {
    }
}
