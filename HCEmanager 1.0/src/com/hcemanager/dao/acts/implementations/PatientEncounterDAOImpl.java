package com.hcemanager.dao.acts.implementations;

import com.hcemanager.dao.acts.interfaces.ActDAO;
import com.hcemanager.dao.acts.interfaces.PatientEncounterDAO;
import com.hcemanager.dao.acts.rowMappers.PatientEncounterRowMapper;
import com.hcemanager.dao.codes.CodeRowMapper;
import com.hcemanager.dao.springServices.SpringServices;
import com.hcemanager.exceptions.acts.*;
import com.hcemanager.exceptions.codes.CodeExistsException;
import com.hcemanager.exceptions.codes.CodeIsNotValidException;
import com.hcemanager.exceptions.codes.CodeNotExistsException;
import com.hcemanager.models.acts.PatientEncounter;
import com.hcemanager.models.codes.Code;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.util.List;

/**
 * @author daniel, esteban.
 */
public class PatientEncounterDAOImpl extends JdbcDaoSupport implements PatientEncounterDAO {

    // -----------------------------------------------------------------------------------------------------------------
    // SQL sentence constants
    // -----------------------------------------------------------------------------------------------------------------

    public static final String INSERT = "insert into PatientEncounter(idPatientEncounter,lengthOfStayQuantity,preAdmitTestInd,Act_idAct) values (?,?,?,?)";
    public static final String UPDATE = "update PatientEncounter set lengthOfStayQuantity=?,preAdmitTestInd=? where idPatientEncounter=?";
    public static final String DELETE = "delete from PatientEncounter where idPatientEncounter=?";
    public static final String SELECT_PATIENT_ENCOUNTER = "select * from PatientEncounter inner join Act where Act_idAct=idAct and  idPatientEncounter=?";
    public static final String SELECT_PATIENT_ENCOUNTERS = "select * from PatientEncounter inner join Act where Act_idAct=idAct";
    public static final String INSERT_CODE = "insert into PatientEncounter_has_Codes (PatientEncounter_idPatientEncounter,Codes_idCodes,type) values(?,?,?)";
    public static final String UPDATE_CODE = "update PatientEncounter_has_Codes set Codes_idCodes = ? where PatientEncounter_idPatientEncounter=? and type=? ";
    public static final String DELETE_CODES = "delete from PatientEncounter_has_Codes where PatientEncounter_idPatientEncounter=?";
    public static final String SELECT_CODES = "select * from PatientEncounter_has_Codes inner join Codes where Codes_idCodes=idCodes and PatientEncounter_idPatientEncounter=?";

    // -----------------------------------------------------------------------------------------------------------------
    // CRUD methods
    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Insert a patient encounter into the db
     * @param patientEncounter a PatientEncounter object
     * @throws PatientEncounterExistsException
     */
    @Override
    public void insertPatientEncounter(PatientEncounter patientEncounter) throws PatientEncounterExistsException, ActExistsException, CodeExistsException {
        try {
            SpringServices.getActDAO().insertAct(patientEncounter);

            getJdbcTemplate().update(INSERT,
                    patientEncounter.getIdPatientEncounter(),
                    patientEncounter.getLengthOfStayQuantity(),
                    patientEncounter.isPreAdmitTestInd(),
                    patientEncounter.getId());
        }catch (DataIntegrityViolationException e){
            throw new PatientEncounterExistsException(e);
        }
        try {
            getJdbcTemplate().update(INSERT_CODE,
                    patientEncounter.getIdPatientEncounter(),
                    patientEncounter.getAdministrationReferralSourceCode().getId(),
                    "administrativeReferralSourceCode");

            getJdbcTemplate().update(INSERT_CODE,
                    patientEncounter.getIdPatientEncounter(),
                    patientEncounter.getDischargeDispositionCode().getId(),
                    "dischargedDispositionCode");

            getJdbcTemplate().update(INSERT_CODE,
                    patientEncounter.getIdPatientEncounter(),
                    patientEncounter.getSpecialCourtesiesCode().getId(),
                    "specialCourtesiesCode");

            getJdbcTemplate().update(INSERT_CODE,
                    patientEncounter.getIdPatientEncounter(),
                    patientEncounter.getSpecialArrangementCode().getId(),
                    "specialArrangementCode");
        }catch (DataIntegrityViolationException e){
            throw new CodeExistsException(e);
        }
    }

    /**
     * Update a patient encounter into the db
     * @param patientEncounter a PatientEncounter object
     * @throws PatientEncounterExistsException
     * @throws PatientEncounterNotExistsException
     */
    @Override
    public void updatePatientEncounter(PatientEncounter patientEncounter) throws PatientEncounterExistsException, PatientEncounterNotExistsException, ActNotExistsException, ActExistsException, CodeIsNotValidException, CodeNotExistsException, CodeExistsException {
        if (isDifferent(patientEncounter)){
            try {
                SpringServices.getActDAO().updateAct(patientEncounter);

                int rows = getJdbcTemplate().update(UPDATE,
                        patientEncounter.getLengthOfStayQuantity(),
                        patientEncounter.isPreAdmitTestInd(),
                        patientEncounter.getIdPatientEncounter());
                if (rows==0){
                    throw new PatientEncounterNotExistsException();
                }

                updateCodes(patientEncounter);
            }catch (DataIntegrityViolationException e){
                throw new PatientEncounterExistsException(e);
            }
        }else {
            SpringServices.getActDAO().updateAct(patientEncounter);
            updateCodes(patientEncounter);
        }
    }

    /**
     * Delete a patent encounter from the db
     * @param id the PatientEncounter id
     * @throws PatientEncounterNotExistsException
     * @throws PatientEncounterCannotBeDeletedException
     */
    @Override
    public void deletePatientEncounter(String id) throws PatientEncounterNotExistsException, PatientEncounterCannotBeDeletedException, ActNotExistsException, ActCannotBeDeleted, CodeIsNotValidException, CodeNotExistsException {
        try {

            getJdbcTemplate().update(DELETE_CODES);

            int rows = getJdbcTemplate().update(DELETE,id);
            if (rows==0){
                throw new PatientEncounterNotExistsException();
            }

            PatientEncounter patientEncounter = getPatientEncounter(id);
            SpringServices.getActDAO().deleteAct(patientEncounter.getId());

        }catch (DataIntegrityViolationException e){
            throw new PatientEncounterCannotBeDeletedException(e);
        }
    }

    /**
     * Get a patient encounter from the db
     * @param id the PatientEncounter id
     * @return patientEncounter
     * @throws PatientEncounterNotExistsException
     */
    @Override
    public PatientEncounter getPatientEncounter(String id) throws PatientEncounterNotExistsException, CodeNotExistsException, CodeIsNotValidException {
        try {
            PatientEncounter patientEncounter =
                    getJdbcTemplate().queryForObject(SELECT_PATIENT_ENCOUNTER,
                            new PatientEncounterRowMapper(), id);
            ActDAO actDAO = SpringServices.getActDAO();
            actDAO.setActCodes(patientEncounter,actDAO.getActCodes(patientEncounter.getId()));
            setPatientEncounterCodes(patientEncounter, getPatientEncounterCodes(id));
            return patientEncounter;

        }catch (EmptyResultDataAccessException e){
            throw new PatientEncounterNotExistsException(e);
        }
    }

    /**
     * Get all the patient encounters from the db
     * @return patient encounters
     * @throws PatientEncounterNotExistsException
     */
    @Override
    public List<PatientEncounter> getPatientEncounters() throws PatientEncounterNotExistsException, CodeNotExistsException, CodeIsNotValidException {
        try {
            List<PatientEncounter> patientEncounters =
                    getJdbcTemplate().query(SELECT_PATIENT_ENCOUNTERS,
                            new PatientEncounterRowMapper());

            for (PatientEncounter patientEncounter:patientEncounters){
                ActDAO actDAO = SpringServices.getActDAO();
                actDAO.setActCodes(patientEncounter,actDAO.getActCodes(patientEncounter.getId()));
                setPatientEncounterCodes(patientEncounter, getPatientEncounterCodes(patientEncounter.getIdPatientEncounter()));
            }
            return patientEncounters;

        }catch (EmptyResultDataAccessException e){
            throw new PatientEncounterNotExistsException(e);
        }
    }

    /**
     * Get a patient encounter codes
     * @param id the PatientEncounter id
     * @return a list with patient encounter codes
     * @throws CodeNotExistsException
     */
    public List<Code> getPatientEncounterCodes(String id) throws CodeNotExistsException {
        try {
            List<Code> codes = getJdbcTemplate().query(SELECT_CODES, new CodeRowMapper(), id);
            return codes;
        }catch (EmptyResultDataAccessException e){
            throw new CodeNotExistsException(e);
        }
    }

    /**
     * Set codes to a PatientEncounter
     * @param patientEncounter a PatientEncounter object
     * @param codes a list with its codes
     * @throws CodeIsNotValidException
     */
    public void setPatientEncounterCodes(PatientEncounter patientEncounter, List<Code> codes) throws CodeIsNotValidException {
        try {
            for (Code code:codes){
                String type=code.getType();

                if (type.equalsIgnoreCase("administrativeReferralSourceCode")){
                    patientEncounter.setAdministrationReferralSourceCode(code);
                }else if (type.equalsIgnoreCase("dischargeDispositionCode")){
                    patientEncounter.setDischargeDispositionCode(code);
                }else if (type.equalsIgnoreCase("specialCourtesiesCode")){
                    patientEncounter.setSpecialCourtesiesCode(code);
                }else if (type.equalsIgnoreCase("specialArrangementCode")){
                    patientEncounter.setSpecialArrangementCode(code);
                }
            }
        }catch (Exception e){
            throw new CodeIsNotValidException(e);
        }
    }

    /**
     * Evaluate if the new patient encounter is different of the old patient encounter
     * @param patientEncounter a PatientEncounter object
     * @return true if the patient encounter is different
     * @throws CodeIsNotValidException
     * @throws CodeNotExistsException
     * @throws PatientEncounterNotExistsException
     */
    private boolean isDifferent(PatientEncounter patientEncounter) throws CodeIsNotValidException, CodeNotExistsException, PatientEncounterNotExistsException {
        PatientEncounter oldPatientEncounter = getPatientEncounter(patientEncounter.getIdPatientEncounter());
        int differentAttrs =0;

        if (!patientEncounter.getLengthOfStayQuantity().equalsIgnoreCase(oldPatientEncounter.getLengthOfStayQuantity()))
            differentAttrs++;
        if (patientEncounter.isPreAdmitTestInd() != oldPatientEncounter.isPreAdmitTestInd())
            differentAttrs++;

        return differentAttrs != 0;
    }

    /**
     * Update the patient encounter codes
     * @param patientEncounter a PatientEncounter object
     * @throws CodeNotExistsException
     * @throws CodeExistsException
     */
    private void updateCodes(PatientEncounter patientEncounter) throws CodeNotExistsException, CodeExistsException {
        List<Code> oldCodes = getPatientEncounterCodes(patientEncounter.getIdPatientEncounter());

        for (Code code:oldCodes){
            try {
                if (code.getType().equalsIgnoreCase("administrativeReferralSourceCode")){
                    if (!code.getId().equals(patientEncounter.getAdministrationReferralSourceCode().getId())){
                        getJdbcTemplate().update(UPDATE_CODE,patientEncounter.getAdministrationReferralSourceCode().getId(),
                                patientEncounter.getIdPatientEncounter(),"administrativeReferralSourceCode");
                    }
                }else if (code.getType().equalsIgnoreCase("dischargeDispositionCode")){
                    if (!code.getId().equals(patientEncounter.getDischargeDispositionCode().getId())){
                        getJdbcTemplate().update(UPDATE_CODE,patientEncounter.getDischargeDispositionCode().getId(),
                                patientEncounter.getIdPatientEncounter(),"dischargeDispositionCode");
                    }
                }else if (code.getType().equalsIgnoreCase("specialCourtesiesCode")){
                    if (!code.getId().equals(patientEncounter.getSpecialCourtesiesCode().getId())){
                        getJdbcTemplate().update(UPDATE_CODE,patientEncounter.getAdministrationReferralSourceCode().getId(),
                                patientEncounter.getIdPatientEncounter(),"specialCourtesiesCode");
                    }
                }else if (code.getType().equalsIgnoreCase("specialArrangementCode")){
                    if (!code.getId().equals(patientEncounter.getSpecialArrangementCode().getId())){
                        getJdbcTemplate().update(UPDATE_CODE, patientEncounter.getAdministrationReferralSourceCode().getId(),
                                patientEncounter.getIdPatientEncounter(), "specialArrangementCode");
                    }
                }
            }catch (DataIntegrityViolationException e){
                throw new CodeExistsException(e);
            }
        }
    }
}
