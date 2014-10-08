package com.hcemanager.exceptions.connectors;

import org.springframework.dao.DataIntegrityViolationException;

/**
 * Created by juan on 10/04/14.
 */
public class ParticipationCannotBeDeletedException extends Throwable {
    public ParticipationCannotBeDeletedException(DataIntegrityViolationException e) {
    }
}
