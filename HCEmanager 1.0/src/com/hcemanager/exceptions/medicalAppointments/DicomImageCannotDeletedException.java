package com.hcemanager.exceptions.medicalAppointments;

import org.springframework.dao.DataIntegrityViolationException;

/**
 * @author Daniel Bellon & Juan Diaz
 */
public class DicomImageCannotDeletedException extends Throwable {
    public DicomImageCannotDeletedException(DataIntegrityViolationException e) {
    }
}
