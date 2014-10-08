package com.hcemanager.exceptions.medicalAppointments;

import org.springframework.dao.EmptyResultDataAccessException;

/**
 * @author Daniel Bellon & Juan Diaz
 */
public class MedicalAppointmentNotExistsException extends Exception {
    public MedicalAppointmentNotExistsException(EmptyResultDataAccessException e) {

    }

    public MedicalAppointmentNotExistsException() {


    }
}
