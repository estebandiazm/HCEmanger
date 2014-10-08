package com.hcemanager.exceptions.users;

import org.springframework.dao.DataIntegrityViolationException;

/**
 * Created by juan on 28/05/14.
 */
public class UserCannotBeDeletedException extends Exception {
    public UserCannotBeDeletedException(DataIntegrityViolationException e) {
    }
}
