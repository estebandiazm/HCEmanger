package com.hcemanager.dao.acts.implementations;

import com.hcemanager.dao.acts.interfaces.ActDAO;
import com.hcemanager.dao.acts.interfaces.ProcedureDAO;
import com.hcemanager.dao.acts.rowMappers.ProcedureRowMapper;
import com.hcemanager.dao.codes.CodeRowMapper;
import com.hcemanager.dao.springServices.SpringServices;
import com.hcemanager.exceptions.acts.*;
import com.hcemanager.exceptions.codes.CodeExistsException;
import com.hcemanager.exceptions.codes.CodeIsNotValidException;
import com.hcemanager.exceptions.codes.CodeNotExistsException;
import com.hcemanager.models.acts.Procedure;
import com.hcemanager.models.codes.Code;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.util.List;

/**
 * @author daniel, esteban.
 */
public class ProcedureDAOImpl extends JdbcDaoSupport implements ProcedureDAO {

    // -----------------------------------------------------------------------------------------------------------------
    // SQL sentences
    // -----------------------------------------------------------------------------------------------------------------

    public static final String INSERT = "insert into Procedure(idProcedure,Act_idAct) values (?,?)";
    public static final String DELETE = "delete from Procedure where idProcedure=?";
    public static final String SELECT_PROCEDURE = "select * from Procedure inner join Act where Act_idAct = idAct and idProcedure=?";
    public static final String SELECT_PROCEDURES = "select * from Procedure inner join Act where Act_idAct = idAct";
    public static final String INSERT_CODE = "insert into Procedure_has_Codes (Procedure_idProcedure,Codes_idCodes,type ) values (?,?,?)";
    public static final String UPDATE_CODE = "update Procedure_has_Codes set Codes_idCodes = ? where Procedure_idProcedure=? and type=?";
    public static final String DELETE_CODES = "delete from Procedure_has_Codes where Procedure_idProcedure=?";
    public static final String SELECT_CODES = "select * from Procedure_has_Codes inner join Codes where Codes_idCodes=idCodes and Procedure_idProcedure=?";

    // -----------------------------------------------------------------------------------------------------------------
    // CRUD methods
    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Insert a procedure into the db
     * @param procedure a Procedure object
     * @throws ProcedureExistsException
     */
    @Override
    public void insertProcedure(Procedure procedure) throws ProcedureExistsException, ActExistsException, CodeExistsException {
        try {
            SpringServices.getActDAO().insertAct(procedure);

            getJdbcTemplate().update(INSERT,
                    procedure.getIdProcedure(),
                    procedure.getId());
        }catch (DataIntegrityViolationException e){
            throw new ProcedureExistsException(e);
        }
        try {
            getJdbcTemplate().update(INSERT_CODE,
                    procedure.getIdProcedure(),
                    procedure.getMethodCode().getId(),
                    "methodCode");

            getJdbcTemplate().update(INSERT_CODE,
                    procedure.getIdProcedure(),
                    procedure.getApproachSiteCode().getId(),
                    "approachSiteCode");

            getJdbcTemplate().update(INSERT_CODE,
                    procedure.getIdProcedure(),
                    procedure.getTargetSiteCode().getId(),
                    "targetSiteCode");
        }catch (DataIntegrityViolationException e){
            throw new CodeExistsException(e);
        }
    }

    /**
     * Update a procedure from the db
     * @param procedure a Procedure object
     * @throws ProcedureExistsException
     * @throws ProcedureNotExistsException
     */
    @Override
    public void updateProcedure(Procedure procedure) throws ProcedureExistsException, ProcedureNotExistsException, ActNotExistsException, ActExistsException, CodeIsNotValidException, CodeNotExistsException, CodeExistsException {
        SpringServices.getActDAO().updateAct(procedure);
        updateCodes(procedure);
    }

    /**
     * Delete a procedure from the db
     * @param id the procedure id
     * @throws ProcedureNotExistsException
     * @throws ProcedureCannotBeDeletedException
     */
    @Override
    public void deleteProcedure(String id) throws ProcedureNotExistsException, ProcedureCannotBeDeletedException, ActNotExistsException, ActCannotBeDeleted, CodeIsNotValidException, CodeNotExistsException {
        try {

            getJdbcTemplate().update(DELETE_CODES,id);

            int rows = getJdbcTemplate().update(DELETE, id);
            if (rows==0){
                throw new ProcedureNotExistsException();
            }

            Procedure procedure = getProcedure(id);
            SpringServices.getActDAO().deleteAct(procedure.getId());

        }catch (DataIntegrityViolationException e){
            throw new ProcedureCannotBeDeletedException(e);
        }
    }

    /**
     * Get a procedure from the db
     * @param id the procedure id
     * @return procedure
     * @throws ProcedureNotExistsException
     */
    @Override
    public Procedure getProcedure(String id) throws ProcedureNotExistsException, CodeNotExistsException, CodeIsNotValidException {
        try {
            Procedure procedure =
                    getJdbcTemplate().queryForObject(SELECT_PROCEDURE, new ProcedureRowMapper(), id);
            ActDAO actDAO = SpringServices.getActDAO();
            actDAO.setActCodes(procedure,actDAO.getActCodes(procedure.getId()));
            setProcedureCodes(procedure,getProcedureCodes(id));
            return procedure;

        }catch (EmptyResultDataAccessException e){
            throw new ProcedureNotExistsException(e);
        }
    }

    /**
     * Get all the procedures from the db
     * @return procedures
     * @throws ProcedureNotExistsException
     */
    @Override
    public List<Procedure> getProcedures() throws ProcedureNotExistsException, CodeNotExistsException, CodeIsNotValidException {
        try {
            List<Procedure> procedures =
                    getJdbcTemplate().query(SELECT_PROCEDURES,new ProcedureRowMapper());

            for (Procedure procedure:procedures){
                ActDAO actDAO = SpringServices.getActDAO();
                actDAO.setActCodes(procedure,actDAO.getActCodes(procedure.getId()));
                setProcedureCodes(procedure,getProcedureCodes(procedure.getIdProcedure()));
            }
            return procedures;

        }catch (EmptyResultDataAccessException e){
            throw new ProcedureNotExistsException(e);
        }
    }

    /**
     * Get a procedure codes
     * @param id the procedure id
     * @return the list whit procedure codes
     * @throws CodeNotExistsException
     */
    public List<Code> getProcedureCodes(String id) throws CodeNotExistsException {
        try {
            List<Code> codes = getJdbcTemplate().query(SELECT_CODES, new CodeRowMapper(), id);
            return codes;
        }catch (EmptyResultDataAccessException e){
            throw new CodeNotExistsException(e);
        }
    }

    /**
     * Set the procedure codes
     * @param procedure a Procedure object
     * @param codes a list with its codes
     * @throws CodeIsNotValidException
     */
    public void setProcedureCodes(Procedure procedure, List<Code> codes) throws CodeIsNotValidException {
        try {
            for (Code code:codes){
                String type=code.getType();

                if (type.equalsIgnoreCase("methodCode")){
                    procedure.setMethodCode(code);
                }else if(type.equalsIgnoreCase("approachSiteCode")){
                    procedure.setApproachSiteCode(code);
                }else if (type.equalsIgnoreCase("targetSiteCode")){
                    procedure.setTargetSiteCode(code);
                }
            }
        }catch (Exception e){
            throw new CodeIsNotValidException(e);
        }
    }

    /**
     * Update the procedure codes
     * @param procedure a Procedure object
     * @throws CodeNotExistsException
     * @throws CodeExistsException
     */
    private void updateCodes(Procedure procedure) throws CodeNotExistsException, CodeExistsException {
        List<Code> oldCodes = getProcedureCodes(procedure.getIdProcedure());

        for (Code code:oldCodes){
            try {
                if (code.getType().equalsIgnoreCase("methodCode")){
                    if (!code.getId().equals(procedure.getMethodCode().getId())){
                        getJdbcTemplate().update(UPDATE_CODE,procedure.getMethodCode().getId(),procedure.getIdProcedure(),
                                "methodCode");
                    }
                }else if (code.getType().equalsIgnoreCase("approachSiteCode")){
                    if (!code.getId().equals(procedure.getApproachSiteCode().getId())){
                        getJdbcTemplate().update(UPDATE_CODE,procedure.getApproachSiteCode().getId(),procedure.getIdProcedure(),
                                "approachSiteCode");
                    }
                }else if (code.getId().equalsIgnoreCase("targetSiteCode")){
                    if (!code.getId().equals(procedure.getTargetSiteCode().getId())){
                        getJdbcTemplate().update(UPDATE_CODE,procedure.getTargetSiteCode().getId(),procedure.getIdProcedure(),
                                "targetSiteCode");
                    }
                }
            }catch (DataIntegrityViolationException e){
                throw new CodeExistsException(e);
            }
        }
    }
}
