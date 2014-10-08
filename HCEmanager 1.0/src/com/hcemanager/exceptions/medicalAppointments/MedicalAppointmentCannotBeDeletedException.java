package com.hcemanager.exceptions.medicalAppointments;

import org.springframework.dao.DataIntegrityViolationException;

/**
 * @author Daniel Bellon & Juan Diaz
 */
public class MedicalAppointmentCannotBeDeletedException extends Exception {
    public MedicalAppointmentCannotBeDeletedException(DataIntegrityViolationException e) {

    }
}
