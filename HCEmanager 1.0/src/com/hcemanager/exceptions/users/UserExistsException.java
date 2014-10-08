package com.hcemanager.exceptions.users;

import org.springframework.dao.DataIntegrityViolationException;

/**
 * Created by juan on 28/05/14.
 */
public class UserExistsException extends Exception {
    public UserExistsException(DataIntegrityViolationException e) {
    }
}
