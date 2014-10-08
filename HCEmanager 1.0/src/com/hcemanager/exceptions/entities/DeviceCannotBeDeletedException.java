package com.hcemanager.exceptions.entities;

import org.springframework.dao.DataIntegrityViolationException;

/**
 * @author daniel, juan.
 */
public class DeviceCannotBeDeletedException extends Exception {
    public DeviceCannotBeDeletedException(DataIntegrityViolationException e) {
    }

    public DeviceCannotBeDeletedException() {
    }
}
