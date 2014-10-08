package com.hcemanager.exceptions.connectors;

import org.springframework.dao.EmptyResultDataAccessException;

/**
 * @author Daniel Bellon & Juan Diaz
 */
public class RoleHasEntityNotExistsException extends Throwable {
    public RoleHasEntityNotExistsException(EmptyResultDataAccessException e) {

    }

    public RoleHasEntityNotExistsException() {

    }
}
