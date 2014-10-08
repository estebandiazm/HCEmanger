package com.hcemanager.dao.medicalAppointments.interfaces;

import com.hcemanager.exceptions.medicalAppointments.MedicalAppointmentCannotBeDeletedException;
import com.hcemanager.exceptions.medicalAppointments.MedicalAppointmentExistsException;
import com.hcemanager.exceptions.medicalAppointments.MedicalAppointmentNotExistsException;
import com.hcemanager.models.medicalAppointments.MedicalAppointment;

import java.util.List;

public interface MedicalAppointmentDAO {

    public void insertMedicalAppointment(MedicalAppointment medicalAppointment) throws MedicalAppointmentExistsException;

    public void updateMedicalAppointment(MedicalAppointment medicalAppointment) throws MedicalAppointmentExistsException;

    public void deleteMedicalAppointment(int idMedicalAppointment) throws MedicalAppointmentCannotBeDeletedException, MedicalAppointmentNotExistsException;

    public MedicalAppointment getMedicalAppointment(int idMedicalAppointment) throws MedicalAppointmentNotExistsException;

    public List<MedicalAppointment> getMedicalAppointments() throws MedicalAppointmentNotExistsException;

    public List<MedicalAppointment> getMedicalAppointmentsByUser(String idUser) throws MedicalAppointmentNotExistsException;

}
