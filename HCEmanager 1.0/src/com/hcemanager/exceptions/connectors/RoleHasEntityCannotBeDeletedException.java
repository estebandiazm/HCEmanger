package com.hcemanager.exceptions.connectors;

import org.springframework.dao.DataIntegrityViolationException;

/**
 * @author Daniel Bellon & Juan Diaz
 */
public class RoleHasEntityCannotBeDeletedException extends Exception {
    public RoleHasEntityCannotBeDeletedException(DataIntegrityViolationException e) {

    }
}
