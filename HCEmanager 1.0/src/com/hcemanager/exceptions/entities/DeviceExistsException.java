package com.hcemanager.exceptions.entities;

import org.springframework.dao.DataIntegrityViolationException;

/**
 * @author daniel, juan.
 */
public class DeviceExistsException extends Exception {
    public DeviceExistsException(DataIntegrityViolationException e) {

    }

    public DeviceExistsException() {
    }
}
