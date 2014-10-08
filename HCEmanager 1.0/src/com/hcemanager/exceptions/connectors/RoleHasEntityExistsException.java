package com.hcemanager.exceptions.connectors;

import org.springframework.dao.DataIntegrityViolationException;

/**
 * @author Daniel Bellon & Juan Diaz
 */
public class RoleHasEntityExistsException extends Throwable {
    public RoleHasEntityExistsException(DataIntegrityViolationException e) {
    }
}
