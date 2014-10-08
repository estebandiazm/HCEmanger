package com.hcemanager.exceptions.acts;

import org.springframework.dao.DataIntegrityViolationException;

/**
 * @author daniel, esteban.
 */
public class AccountCannotBeDeleted extends Exception {
    public AccountCannotBeDeleted(DataIntegrityViolationException e) {
    }
}
