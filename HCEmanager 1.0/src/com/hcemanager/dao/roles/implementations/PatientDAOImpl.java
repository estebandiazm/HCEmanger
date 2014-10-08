package com.hcemanager.dao.roles.implementations;

import com.hcemanager.dao.codes.CodeRowMapper;
import com.hcemanager.dao.roles.interfaces.PatientDAO;
import com.hcemanager.dao.roles.rowMappers.PatientRowMapper;
import com.hcemanager.dao.springServices.SpringServices;
import com.hcemanager.exceptions.codes.CodeExistsException;
import com.hcemanager.exceptions.codes.CodeNotExistsException;
import com.hcemanager.exceptions.roles.*;
import com.hcemanager.models.codes.Code;
import com.hcemanager.models.roles.Patient;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.util.List;

/**
 * @author Daniel Bellon & Esteban Diaz
 */
public class PatientDAOImpl extends JdbcDaoSupport implements PatientDAO {

    //------------------------------------------------------------------------------------------------------------------
    //SQL sentence constants.
    //------------------------------------------------------------------------------------------------------------------

    public static final String INSERT = "insert into Patient(idPatient,Role_idRole) values (?,?);";
    public static final String DELETE = "delete from Patient where idPatient=?";
    public static final String SELECT_PATIENT = "select * from Patient inner join Role where Role_idRole=idRole and idPatient=?";
    public static final String SELECT_PATIENTS = "select * from Patient inner join Role where Role_idRole=idRole";
    public static final String INSERT_CODE = "insert into Patient_has_Codes (Codes_idCodes,Patient_idPatient,type) values (?,?,?);";
    public static final String UPDATE_CODE = "update Patient_has_Codes set Codes_idCodes=? where Patient_idPatient=? and type=?;";
    public static final String DELETE_CODE = "delete from Patient_has_Codes where Patient_idPatient=?";
    public static final String SELECT_CODES = "select * from Patient_has_Codes inner join Codes where Codes_idCodes=idCodes " +
            "and Patient_idPatient=?";



    //------------------------------------------------------------------------------------------------------------------
    //CRUD methods.
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Insert an Patient.
     * @param patient a Patient object
     * @throws PatientExistsException
     */
    @Override
    public void insertPatient(Patient patient) throws PatientExistsException, RoleExistsException {
        try {

            SpringServices.getRoleDAO().insertRole(patient);

            getJdbcTemplate().update(INSERT,
                    patient.getIdPatient(),
                    patient.getId());
            
            //Codes
            
            getJdbcTemplate().update(INSERT_CODE,
                    patient.getVeryImportantPersonCode().getId(),
                    patient.getIdPatient(),
                    patient.getVeryImportantPersonCode().getType());
            
        }catch (DataIntegrityViolationException e){
            throw new PatientExistsException(e);
        }
    }

    /**
     * Update an Patient.
     * @param patient a Patient object
     * @throws PatientNotExistsException
     * @throws PatientExistsException
     */
    @Override
    public void updatePatient(Patient patient) throws PatientNotExistsException, PatientExistsException, RoleExistsException, RoleNotExistsException, CodeNotExistsException, CodeExistsException {
        try {

            SpringServices.getRoleDAO().updateRole(patient);

            updateCodes(patient);

        }catch (DataIntegrityViolationException e){
            throw new PatientExistsException(e);
        }

    }

    /**
     * Delete an Patient.
     * @param id the Patient id
     * @throws PatientNotExistsException
     * @throws PatientCannotBeDeleted
     */
    @Override
    public void deletePatient(String id) throws PatientNotExistsException, PatientCannotBeDeleted, RoleNotExistsException, RoleCannotBeDeletedException, CodeNotExistsException {
        try {

            getJdbcTemplate().update(DELETE_CODE);

            int rows = getJdbcTemplate().update(DELETE, id);
            if(rows==0){
                throw new PatientNotExistsException();
            }

            Patient patient = getPatient(id);
            SpringServices.getRoleDAO().deleteRole(patient.getId());

        }catch (DataIntegrityViolationException e){
            throw new PatientCannotBeDeleted(e);
        }
    }

    /**
     * Get a Patient.
     * @param id the Patient id
     * @return Patient
     * @throws PatientNotExistsException
     */
    @Override
    public Patient getPatient(String id) throws PatientNotExistsException, CodeNotExistsException {
        try {
            Patient patient = getJdbcTemplate().queryForObject(SELECT_PATIENT, new PatientRowMapper(), id);
            setPatientCodes(patient, getPatientCodes(id));
            return patient;
        }catch (EmptyResultDataAccessException e){
            throw new PatientNotExistsException(e);
        }
    }

    /**
     * Get all Patients.
     * @return List<Patient>
     * @throws PatientNotExistsException
     */
    @Override
    public List<Patient> getPatients() throws PatientNotExistsException, CodeNotExistsException {
        try {
            List<Patient> patients = getJdbcTemplate().query(SELECT_PATIENTS, new PatientRowMapper());
            for (Patient patient:patients){
                setPatientCodes(patient, getPatientCodes(patient.getIdPatient()));
                patients.set(patients.indexOf(patient),patient);
            }
            return patients;
        }catch (EmptyResultDataAccessException e){
            throw new PatientNotExistsException(e);
        }
    }

    /**
     * Get the Codes of Patient by id
     * @param id
     * @return
     * @throws CodeNotExistsException
     */
    public List<Code> getPatientCodes(String id) throws CodeNotExistsException {
        try {
            List<Code> codes = getJdbcTemplate().query(SELECT_CODES, new CodeRowMapper(), id);
            return codes;
        }catch (EmptyResultDataAccessException e){
            throw new CodeNotExistsException(e);
        }
    }

    /**
     * Set the Codes to Patient.
     * @param patient
     * @param codes
     * @return
     */
    public void setPatientCodes(Patient patient, List<Code> codes) throws CodeNotExistsException {

        SpringServices.getRoleDAO().setRoleCodes(patient, SpringServices.getRoleDAO().getRoleCodes(patient.getId()));

        for (Code code:codes){

            String typeCode = code.getType();

            if (typeCode.equals("veryImportantPersonCode")){
                patient.setVeryImportantPersonCode(code);
            }
        }
    }

    private void updateCodes(Patient patient) throws CodeNotExistsException, CodeExistsException {
        List<Code> oldCodes = getPatientCodes(patient.getIdPatient());

        for (Code code:oldCodes){
            try {
                if (code.getType().equalsIgnoreCase(patient.getVeryImportantPersonCode().getType())){
                    if (!code.getId().equals(patient.getVeryImportantPersonCode().getId())){
                        getJdbcTemplate().update(UPDATE_CODE,
                                patient.getVeryImportantPersonCode().getId(),
                                patient.getIdPatient(),
                                patient.getVeryImportantPersonCode().getType());
                    }
                }
            }catch (DataIntegrityViolationException e){
                throw new CodeExistsException(e);
            }
        }

    }
}
