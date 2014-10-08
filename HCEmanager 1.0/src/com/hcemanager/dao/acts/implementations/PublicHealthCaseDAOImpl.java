package com.hcemanager.dao.acts.implementations;

import com.hcemanager.dao.acts.interfaces.ActDAO;
import com.hcemanager.dao.acts.interfaces.ObservationDAO;
import com.hcemanager.dao.acts.rowMappers.PublicHealthCaseRowMapper;
import com.hcemanager.dao.acts.interfaces.PublicHealthCaseDAO;
import com.hcemanager.dao.codes.CodeRowMapper;
import com.hcemanager.dao.springServices.SpringServices;
import com.hcemanager.exceptions.acts.*;
import com.hcemanager.exceptions.codes.CodeExistsException;
import com.hcemanager.exceptions.codes.CodeIsNotValidException;
import com.hcemanager.exceptions.codes.CodeNotExistsException;
import com.hcemanager.models.acts.PublicHealthCase;
import com.hcemanager.models.codes.Code;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.util.List;

/**
 * @author daniel, esteban.
 */
public class PublicHealthCaseDAOImpl extends JdbcDaoSupport implements PublicHealthCaseDAO {

    // -----------------------------------------------------------------------------------------------------------------
    // SQL sentences
    // -----------------------------------------------------------------------------------------------------------------

    public static final String INSERT = "insert into PublicHealthCase(idPublicHealthCase,Observation_idObservation) values (?,?)";
    public static final String DELETE = "delete from PubicHealthCase where idPublicHealthCase=?";
    public static final String SELECT_PUBLIC_HEALTH_CASE = "select * from PublicHealthCase inner join Observation inner join Act  where Observation_idObservation=idObservation and  Act_idAct= idAct and idPublicHealthCase=?";
    public static final String SELECT_PUBLIC_HEALTH_CASES = "select * from PublicHealthCase inner join Observation inner join Act where Observation_idObservation=idObservation and Act_idAct=idAct";
    public static final String INSERT_CODE = "insert into PublicHealthCase_has_Codes (PublicHealthCase_idPublicHealthCase,Codes_idCodes,type) values (?,?,?)";
    public static final String UPDATE_CODE = "update PublicHealthCase_has_Codes set Codes_idCodes = ? where PublicHealthCase_idPublicHealthCase=? and type=? ";
    public static final String DELETE_CODES = "delete from PublicHealthCase_has_Codes where PublicHealthCase_idPublicHealthCase =?";
    public static final String SELECT_CODES = "select * from PublicHealthCase_has_Codes inner join Codes where Codes_idCodes=idCodes and PublicHealthCase_idPublicHealthCase =?";

    // -----------------------------------------------------------------------------------------------------------------
    // CRUD methods
    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Insert a public health case into the db
     * @param publicHealthCase a PublicHealthCase object
     * @throws PublicHealthCaseExistsException
     */
    @Override
    public void insertPublicHealthCase(PublicHealthCase publicHealthCase) throws PublicHealthCaseExistsException, ObservationExistsException, ActExistsException, CodeExistsException {
        try {
            SpringServices.getObservationDAO().insertObservation(publicHealthCase);

            getJdbcTemplate().update(INSERT,
                    publicHealthCase.getIdPublicHealthCase(),
                    publicHealthCase.getIdObservation());
        }catch (DataIntegrityViolationException e){
            throw new PublicHealthCaseExistsException(e);
        }
        try {
            getJdbcTemplate().update(INSERT_CODE,
                    publicHealthCase.getIdPublicHealthCase(),
                    publicHealthCase.getDetectionMethodCode().getId(),
                    "detectionMethodCode");

            getJdbcTemplate().update(INSERT_CODE,
                    publicHealthCase.getIdPublicHealthCase(),
                    publicHealthCase.getTransmissionModeCode().getId(),
                    "transmissionModeCode");

            getJdbcTemplate().update(INSERT_CODE,
                    publicHealthCase.getIdPublicHealthCase(),
                    publicHealthCase.getDiseaseImportCode().getId(),
                    "diseaseImportCode");
        }catch (DataIntegrityViolationException e){
            throw new CodeExistsException(e);
        }
    }

    /**
     * Update a public health case from the db
     * @param publicHealthCase a PublicHealthCase object
     * @throws PublicHealthCaseExistsException
     * @throws PublicHealthCaseNotExistsException
     */
    @Override
    public void updatePublicHealthCase(PublicHealthCase publicHealthCase) throws PublicHealthCaseExistsException, PublicHealthCaseNotExistsException, ActExistsException, ActNotExistsException, ObservationNotExistsException, ObservationExistsException, CodeIsNotValidException, CodeNotExistsException, CodeExistsException {
        SpringServices.getObservationDAO().updateObservation(publicHealthCase);
        updateCodes(publicHealthCase);
    }

    /**
     * Delete a public health case form the db
     * @param id the public health case id
     * @throws PublicHealthCaseNotExistsException
     * @throws PublicHealthCaseCannotBeDeletedException
     */
    @Override
    public void deletePublicHealthCase(String id) throws PublicHealthCaseNotExistsException, PublicHealthCaseCannotBeDeletedException, ObservationCannotBeDeleteException, ActCannotBeDeleted, ObservationNotExistsException, ActNotExistsException, CodeIsNotValidException, CodeNotExistsException {
        try {

            getJdbcTemplate().update(DELETE_CODES,id);

            int rows = getJdbcTemplate().update(DELETE, id);
            if (rows==0){
                throw new PublicHealthCaseNotExistsException();
            }

            PublicHealthCase publicHealthCase = getPublicHealthCase(id);
            SpringServices.getObservationDAO().deleteObservation(publicHealthCase.getIdObservation());

        }catch (DataIntegrityViolationException e){
            throw new PublicHealthCaseCannotBeDeletedException(e);
        }
    }

    /**
     * Get a public health case
     * @param id the public health case id
     * @return the public health case
     * @throws PublicHealthCaseNotExistsException
     * @throws CodeNotExistsException
     * @throws CodeIsNotValidException
     */
    public PublicHealthCase getPublicHealthCase(String id) throws PublicHealthCaseNotExistsException, CodeNotExistsException, CodeIsNotValidException {
        try {

            PublicHealthCase publicHealthCase =
                    getJdbcTemplate().queryForObject(SELECT_PUBLIC_HEALTH_CASE,
                            new PublicHealthCaseRowMapper(), id);
            ActDAO actDAO = SpringServices.getActDAO();
            ObservationDAO observationDAO = SpringServices.getObservationDAO();
            actDAO.setActCodes(publicHealthCase,actDAO.getActCodes(publicHealthCase.getId()));
            observationDAO.setObservationCodes(publicHealthCase,observationDAO.getObservationCodes(publicHealthCase.getIdObservation()));
            setPublicHealthCaseCodes(publicHealthCase, getPublicHealthCaseCodes(id));

            return publicHealthCase;

        }catch (EmptyResultDataAccessException e){
            throw new PublicHealthCaseNotExistsException(e);
        }
    }

    /**
     * Get all the public health cases form the db
     * @return public health cases
     * @throws PublicHealthCaseNotExistsException
     */
    @Override
    public List<PublicHealthCase> getPublicHealthCases() throws PublicHealthCaseNotExistsException, CodeNotExistsException, CodeIsNotValidException {
        try {
            List<PublicHealthCase> publicHealthCases =
                    getJdbcTemplate().query(SELECT_PUBLIC_HEALTH_CASES,new PublicHealthCaseRowMapper());

            for (PublicHealthCase publicHealthCase:publicHealthCases){
                ActDAO actDAO = SpringServices.getActDAO();
                ObservationDAO observationDAO = SpringServices.getObservationDAO();
                actDAO.setActCodes(publicHealthCase,actDAO.getActCodes(publicHealthCase.getId()));
                observationDAO.setObservationCodes(publicHealthCase,observationDAO.getObservationCodes(publicHealthCase.getIdObservation()));
                setPublicHealthCaseCodes(publicHealthCase, getPublicHealthCaseCodes(publicHealthCase.getIdPublicHealthCase()));
            }

            return publicHealthCases;

        }catch (EmptyResultDataAccessException e){
            throw new PublicHealthCaseNotExistsException(e);
        }
    }

    /**
     * Get the public health case codes
     * @param id the public health case id
     * @return a list with its codes
     * @throws CodeNotExistsException
     */
    public List<Code> getPublicHealthCaseCodes(String id) throws CodeNotExistsException {
        try {
            List<Code> codes = getJdbcTemplate().query(SELECT_CODES, new CodeRowMapper(), id);
            return codes;
        }catch (EmptyResultDataAccessException e){
            throw new CodeNotExistsException(e);
        }
    }

    /**
     * Set the public health case codes
     * @param publicHealthCase a PublicHealthCase object
     * @param codes a list of Public health case codes
     * @throws CodeIsNotValidException
     */
    public void setPublicHealthCaseCodes(PublicHealthCase publicHealthCase,List<Code> codes) throws CodeIsNotValidException {
        try {
            for (Code code:codes){
                String type=code.getType();

                if (type.equalsIgnoreCase("detectionMethodCode")){
                    publicHealthCase.setDetectionMethodCode(code);
                }else if (type.equalsIgnoreCase("transmissionModeCode")){
                    publicHealthCase.setTransmissionModeCode(code);
                }else if (type.equalsIgnoreCase("diseaseImportCode")){
                    publicHealthCase.setDiseaseImportCode(code);
                }
            }
        }catch (Exception e){
            throw new CodeIsNotValidException(e);
        }
    }

    /**
     * Update the public health case codes
     * @param publicHealthCase a PublicHealthCase object
     * @throws CodeNotExistsException
     * @throws CodeExistsException
     */
    private void updateCodes(PublicHealthCase publicHealthCase) throws CodeNotExistsException, CodeExistsException {
        List<Code> oldCodes = getPublicHealthCaseCodes(publicHealthCase.getIdPublicHealthCase());

        for (Code code:oldCodes){
            try {
                if (code.getType().equalsIgnoreCase("detectionMethodCode")){
                    if (!code.getId().equals(publicHealthCase.getDetectionMethodCode().getId())){
                        getJdbcTemplate().update(UPDATE_CODE,publicHealthCase.getDetectionMethodCode().getId(),
                                publicHealthCase.getIdPublicHealthCase(),"detectionMethodCode");
                    }
                }else if (code.getType().equalsIgnoreCase("transmissionModeCode")){
                    if (!code.getId().equals(publicHealthCase.getTransmissionModeCode().getId())){
                        getJdbcTemplate().update(UPDATE_CODE,publicHealthCase.getTransmissionModeCode().getId(),
                                publicHealthCase.getIdPublicHealthCase(),"transmissionModeCode");
                    }
                }else if (code.getType().equalsIgnoreCase("diseaseImportCode")){
                    if (!code.getId().equals(publicHealthCase.getDiseaseImportCode().getId())){
                        getJdbcTemplate().update(UPDATE_CODE,publicHealthCase.getDiseaseImportCode().getId(),
                                publicHealthCase.getIdPublicHealthCase(),"diseaseImportCode");
                    }
                }
            }catch (DataIntegrityViolationException e){
                throw new CodeExistsException(e);
            }
        }
    }
}
