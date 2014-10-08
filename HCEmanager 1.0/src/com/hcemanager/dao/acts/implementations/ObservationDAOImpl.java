package com.hcemanager.dao.acts.implementations;

import com.hcemanager.dao.acts.interfaces.ObservationDAO;
import com.hcemanager.dao.acts.rowMappers.ObservationRowMapper;
import com.hcemanager.dao.codes.CodeRowMapper;
import com.hcemanager.dao.springServices.SpringServices;
import com.hcemanager.exceptions.acts.*;
import com.hcemanager.exceptions.codes.CodeExistsException;
import com.hcemanager.exceptions.codes.CodeIsNotValidException;
import com.hcemanager.exceptions.codes.CodeNotExistsException;
import com.hcemanager.models.acts.Observation;
import com.hcemanager.models.codes.Code;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.util.List;

/**
 * Implementation Observation DAO
 * @author Daniel Bellon & Juan Diaz
 */
public class ObservationDAOImpl extends JdbcDaoSupport implements ObservationDAO {

    // -----------------------------------------------------------------------------------------------------------------
    // SQL sentence constants
    // -----------------------------------------------------------------------------------------------------------------

    public static final String INSERT = "insert into Observation(idObservation,valueObservation,Act_idAct) values (?,?,?)";
    public static final String UPDATE = "update Observation set valueObservation=?  where idObservation=?,";
    public static final String DELETE = "delete from Observation where idObservation=?";
    public static final String SELECT_OBSERVATION = "select * from Observation inner join Act where Act_idAct=idAct and idObservation=?";
    public static final String SELECT_OBSERVATIONS = "select * from Observation inner join Act where Act_idAct=idAct";
    public static final String INSERT_CODE = "insert into Observation_hasCodes (Observation_idObservation,Codes_idCodes,type) values (?,?,?)";
    public static final String UPDATE_CODE = "update Observation_has_Codes set Codes_idCodes = ? where Observation_idObservation=? and type =? ";
    public static final String DELETE_CODES = "delete from Observation_has_Codes where Observation_idObservation=?";
    public static final String SELECT_CODES = "select * from Observation_has_Codes inner join Codes where Codes_idCodes=idCodes and Observation_idObservation=?";

    // -----------------------------------------------------------------------------------------------------------------
    // CRUD methods
    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Insert a observation into the db
     * @param observation a Observation object
     * @throws ObservationExistsException
     */
    @Override
    public void insertObservation(Observation observation) throws ObservationExistsException, ActExistsException, CodeExistsException {
        try {
            SpringServices.getActDAO().insertAct(observation);

            getJdbcTemplate().update(INSERT,
                    observation.getIdObservation(),
                    observation.getValueObservation(),
                    observation.getId());
        }catch (DataIntegrityViolationException e){
            throw new ObservationExistsException(e);
        }
        try {
            getJdbcTemplate().update(INSERT_CODE,
                    observation.getIdObservation(),
                    observation.getInterpretationCode().getId(),
                    "interpretationCode");

            getJdbcTemplate().update(INSERT_CODE,
                    observation.getIdObservation(),
                    observation.getMethodCode().getId(),
                    "methodCode");

            getJdbcTemplate().update(INSERT_CODE,
                    observation.getIdObservation(),
                    observation.getTargetSiteCode(),
                    "targetSiteCode");
        }catch (DataIntegrityViolationException e){
            throw new CodeExistsException(e);
        }
    }

    /**
     * Update an observation into the db
     * @param observation an Observation object
     * @throws ObservationExistsException
     * @throws ObservationNotExistsException
     */
    @Override
    public void updateObservation(Observation observation) throws ObservationExistsException, ObservationNotExistsException, ActNotExistsException, ActExistsException, CodeIsNotValidException, CodeNotExistsException, CodeExistsException {
        if (isDifferent(observation)){
            try {

                SpringServices.getActDAO().updateAct(observation);

                int rows = getJdbcTemplate().update(UPDATE,
                        observation.getValueObservation(),
                        observation.getIdObservation());
                if (rows==0){
                    throw new ObservationNotExistsException();
                }

             updateCodes(observation);

            }catch (DataIntegrityViolationException e){
                throw new ObservationExistsException(e);
            }
        }else {
            SpringServices.getActDAO().updateAct(observation);
            updateCodes(observation);
        }
    }

    /**
     * Delete an Observation from the db
     * @param id the observation id
     * @throws ObservationNotExistsException
     * @throws ObservationCannotBeDeleteException
     */
    @Override
    public void deleteObservation(String id) throws ObservationNotExistsException, ObservationCannotBeDeleteException, ActNotExistsException, ActCannotBeDeleted {
        try {

            getJdbcTemplate().update(DELETE_CODES,id);

            int rows = getJdbcTemplate().update(DELETE,id);
            if (rows==0){
                throw new ObservationNotExistsException();
            }

            Observation observation = getObservation(id);
            SpringServices.getActDAO().deleteAct(observation.getId());

        }catch (DataIntegrityViolationException e){
            throw new ObservationCannotBeDeleteException(e);
        }
    }

    /**
     * Get an observation
     * @param id the id observation
     * @return observation
     * @throws ObservationNotExistsException
     */
    @Override
    public Observation getObservation(String id) throws ObservationNotExistsException {
        try {
            Observation observation =
                    getJdbcTemplate().queryForObject(SELECT_OBSERVATION, new ObservationRowMapper(), id);
            return observation;

        }catch (EmptyResultDataAccessException e){
            throw new ObservationNotExistsException(e);
        }
    }

    /**
     * Get all the observation from the db
     * @return observations
     * @throws ObservationNotExistsException
     */
    @Override
    public List<Observation> getObservations() throws ObservationNotExistsException {
        try {
            List<Observation> observations =
                    getJdbcTemplate().query(SELECT_OBSERVATIONS, new ObservationRowMapper());
            return observations;

        }catch (EmptyResultDataAccessException e){
            throw new ObservationNotExistsException(e);
        }
    }

    /**
     * Get observation codes
     * @param id the observation id
     * @return a observation codes list
     * @throws CodeNotExistsException
     */
    @Override
    public List<Code> getObservationCodes(String id) throws CodeNotExistsException {
        try {
            List<Code> codes = getJdbcTemplate().query(SELECT_CODES, new CodeRowMapper(), id);
            return codes;
        }catch (EmptyResultDataAccessException e){
            throw new CodeNotExistsException(e);
        }
    }

    /**
     * Set codes to a observation
     * @param observation an Observation object
     * @param codes the list of observation codes
     * @return A observation with its codes
     * @throws CodeIsNotValidException
     */
    @Override
    public void setObservationCodes(Observation observation, List<Code> codes) throws CodeIsNotValidException {
        try {
            for (Code code:codes){
                String typeCode= code.getType();
                if (typeCode.equalsIgnoreCase("interpretationCode")){
                    observation.setInterpretationCode(code);
                }else if (typeCode.equalsIgnoreCase("methodCode")){
                    observation.setMethodCode(code);
                }else if (typeCode.equalsIgnoreCase("targetSiteCode")){
                    observation.setTargetSiteCode(code);
                }
            }

        }catch (Exception e){
            throw new CodeIsNotValidException(e);
        }
    }

    /**
     * Evaluate if the new observation is different of the old observation
     * @param observation an Observation object
     * @return true if the observation is different
     * @throws ObservationNotExistsException
     */
    private boolean isDifferent(Observation observation) throws ObservationNotExistsException {
        Observation oldObservation = getObservation(observation.getIdObservation());
        int differentAttrs=0;

        if (!observation.getValueObservation().equalsIgnoreCase(oldObservation.getValueObservation()))
            differentAttrs++;

        return differentAttrs !=0;
    }

    /**
     * Update the observation codes
     * @param observation an Observation object
     * @throws CodeNotExistsException
     * @throws CodeExistsException
     */
    private void updateCodes(Observation observation) throws CodeNotExistsException, CodeExistsException {
        List<Code> oldCodes = getObservationCodes(observation.getIdObservation());

        for (Code code:oldCodes){
            if (code.getType().equalsIgnoreCase("interpretationCode")){
                if (!code.getId().equals(observation.getInterpretationCode().getId())){
                    try {
                        getJdbcTemplate().update(UPDATE_CODE,observation.getInterpretationCode().getId(),
                                observation.getIdObservation(),"interpretationCode");
                    }catch (DataIntegrityViolationException e){
                        throw new CodeExistsException(e);
                    }
                }
            }else if (code.getType().equalsIgnoreCase("methodCode")){
                if (!code.getId().equals(observation.getModeCode().getId())){
                    try {
                        getJdbcTemplate().update(UPDATE_CODE, observation.getMethodCode().getId(),
                                observation.getIdObservation(),"methodCode");
                    }catch (DataIntegrityViolationException e){
                        throw new CodeExistsException(e);
                    }
                }
            }else if (code.getType().equalsIgnoreCase("targetSIteCode")){
                if (!code.getId().equals(observation.getTargetSiteCode().getId())){
                    try {
                        getJdbcTemplate().update(UPDATE_CODE,observation.getTargetSiteCode().getId(),
                                observation.getIdObservation(),"targetSiteCode");
                    }catch (DataIntegrityViolationException e){
                        throw new CodeExistsException(e);
                    }
                }
            }
        }
    }
}
