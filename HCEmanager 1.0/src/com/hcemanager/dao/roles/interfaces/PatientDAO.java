package com.hcemanager.dao.roles.interfaces;

import com.hcemanager.exceptions.codes.CodeExistsException;
import com.hcemanager.exceptions.codes.CodeNotExistsException;
import com.hcemanager.exceptions.roles.*;
import com.hcemanager.models.codes.Code;
import com.hcemanager.models.roles.Patient;

import java.util.List;

/**
 * @author daniel, esteban.
 */
public interface PatientDAO {

    public void insertPatient(Patient patient) throws PatientExistsException, RoleExistsException;
    public void updatePatient(Patient patient) throws PatientNotExistsException, PatientExistsException,
            RoleExistsException, RoleNotExistsException, CodeNotExistsException, CodeExistsException;
    public void deletePatient(String id) throws PatientNotExistsException, PatientCannotBeDeleted,
            RoleNotExistsException, RoleCannotBeDeletedException, CodeNotExistsException;
    public Patient getPatient(String id) throws PatientNotExistsException, CodeNotExistsException;
    public List<Patient> getPatients() throws PatientNotExistsException, CodeNotExistsException;
}
