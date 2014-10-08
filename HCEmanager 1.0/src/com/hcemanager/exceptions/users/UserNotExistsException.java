package com.hcemanager.exceptions.users;

import org.springframework.dao.EmptyResultDataAccessException;

/**
 * Created by juan on 28/05/14.
 */
public class UserNotExistsException extends Exception {
    public UserNotExistsException(EmptyResultDataAccessException e) {

    }

    public UserNotExistsException() {

    }
}
