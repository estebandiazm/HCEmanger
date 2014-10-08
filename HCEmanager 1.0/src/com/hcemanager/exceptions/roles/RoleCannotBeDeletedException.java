package com.hcemanager.exceptions.roles;

import org.springframework.dao.DataIntegrityViolationException;

/**
 * Created by juan on 23/04/14.
 */
public class RoleCannotBeDeletedException extends Exception {
    public RoleCannotBeDeletedException(DataIntegrityViolationException e) {

    }
}
