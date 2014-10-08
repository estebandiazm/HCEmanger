package com.hcemanager.exceptions.connectors;

import org.springframework.dao.EmptyResultDataAccessException;

/**
 * Created by juan on 10/04/14.
 */
public class ParticipationNotExistsException extends Exception {
    public ParticipationNotExistsException(EmptyResultDataAccessException e) {

    }

    public ParticipationNotExistsException() {

    }
}
