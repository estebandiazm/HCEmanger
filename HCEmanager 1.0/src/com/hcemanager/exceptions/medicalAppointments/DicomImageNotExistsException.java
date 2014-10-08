package com.hcemanager.exceptions.medicalAppointments;

import org.springframework.dao.EmptyResultDataAccessException;

/**
 * @author Daniel Bellon & Juan Diaz
 */
public class DicomImageNotExistsException extends Throwable {
    public DicomImageNotExistsException() {
    }

    public DicomImageNotExistsException(EmptyResultDataAccessException e) {

    }
}
