package com.hcemanager.exceptions.medicalAppointments;

import org.springframework.dao.DataIntegrityViolationException;

/**
 * @author Daniel Bellon & Juan Diaz
 */
public class MedicalAppointmentExistsException extends Exception {
    public MedicalAppointmentExistsException(DataIntegrityViolationException e) {
    }
}
