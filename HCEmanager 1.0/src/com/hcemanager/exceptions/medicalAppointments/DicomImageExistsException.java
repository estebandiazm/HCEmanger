package com.hcemanager.exceptions.medicalAppointments;

import org.springframework.dao.DataIntegrityViolationException;

/**
 * @author Daniel Bellon & Juan Diaz
 */
public class DicomImageExistsException extends Throwable {
    public DicomImageExistsException(DataIntegrityViolationException e) {
    }
}
