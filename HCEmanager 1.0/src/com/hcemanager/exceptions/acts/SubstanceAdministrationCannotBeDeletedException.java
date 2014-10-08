package com.hcemanager.exceptions.acts;

import org.springframework.dao.DataIntegrityViolationException;

/**
 * @author daniel, esteban.
 */
public class SubstanceAdministrationCannotBeDeletedException extends Throwable {
    public SubstanceAdministrationCannotBeDeletedException(DataIntegrityViolationException e) {
    }
}
