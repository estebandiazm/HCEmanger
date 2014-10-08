package com.hcemanager.exceptions.acts;

import org.springframework.dao.DataIntegrityViolationException;

/**
 * @author daniel, esteban.
 */
public class DeviceTaskCannotBeDeleted extends Throwable {

    /**
     * Constructor with an exception argument
     * @param e
     */
    public DeviceTaskCannotBeDeleted(DataIntegrityViolationException e) {
    }
}
