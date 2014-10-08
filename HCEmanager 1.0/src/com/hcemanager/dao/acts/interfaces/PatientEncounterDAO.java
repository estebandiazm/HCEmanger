package com.hcemanager.dao.acts.interfaces;

import com.hcemanager.exceptions.acts.*;
import com.hcemanager.exceptions.codes.CodeExistsException;
import com.hcemanager.exceptions.codes.CodeIsNotValidException;
import com.hcemanager.exceptions.codes.CodeNotExistsException;
import com.hcemanager.models.acts.PatientEncounter;

import java.util.List;

/**
 * This interface define the Patient encounter DAO methods
 * @author daniel, esteban.
 */
public interface PatientEncounterDAO {

    public void insertPatientEncounter(PatientEncounter patientEncounter) throws PatientEncounterExistsException, ActExistsException, CodeExistsException;
    public void updatePatientEncounter(PatientEncounter patientEncounter) throws PatientEncounterExistsException, PatientEncounterNotExistsException, ActNotExistsException, ActExistsException, CodeIsNotValidException, CodeNotExistsException, CodeExistsException;
    public void deletePatientEncounter(String id) throws PatientEncounterNotExistsException, PatientEncounterCannotBeDeletedException, ActNotExistsException, ActCannotBeDeleted, CodeIsNotValidException, CodeNotExistsException;
    public PatientEncounter getPatientEncounter(String id) throws PatientEncounterNotExistsException, CodeNotExistsException, CodeIsNotValidException;
    public List<PatientEncounter> getPatientEncounters() throws PatientEncounterNotExistsException, CodeNotExistsException, CodeIsNotValidException;
}
