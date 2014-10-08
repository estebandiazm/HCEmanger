package com.hcemanager.dao.acts.implementations;

import com.hcemanager.dao.acts.interfaces.ActDAO;
import com.hcemanager.dao.acts.interfaces.SubstanceAdministrationDAO;
import com.hcemanager.dao.acts.rowMappers.SubstanceAdministrationRowMapper;
import com.hcemanager.dao.codes.CodeRowMapper;
import com.hcemanager.dao.springServices.SpringServices;
import com.hcemanager.exceptions.acts.*;
import com.hcemanager.exceptions.codes.CodeExistsException;
import com.hcemanager.exceptions.codes.CodeIsNotValidException;
import com.hcemanager.exceptions.codes.CodeNotExistsException;
import com.hcemanager.models.acts.SubstanceAdministration;
import com.hcemanager.models.codes.Code;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.util.List;

/**
 * @author daniel, esteban.
 */
public class SubstanceAdministrationDAOImpl extends JdbcDaoSupport implements SubstanceAdministrationDAO {

    // -----------------------------------------------------------------------------------------------------------------
    // SQL sentences
    // -----------------------------------------------------------------------------------------------------------------

    public static final String INSERT = "insert into SubstanceAdministration(idSubstanceAdministration,maxDoseQuantity,doseCheckQuantity,rateQuantity,doseQuantity,Act_idAct)values(?,?,?,?,?,?)";
    public static final String UPDATE = "update SubstanceAdministration set maxDoseQuantity=?,doseCheckQuantity=?,rateQuantity=?,doseQuantity=? where idSubstanceAdministration=?";
    public static final String DELETE = "delete from SubstanceAdministration where idSubstanceAdministration=?";
    public static final String SELECT_SUBSTANCE_ADMINISTRATION = "select * from SubstanceAdministration inner join Act where Act_idAct=idAct and  idSubstanceAdministration=?";
    public static final String SELECT_SUBSTANCE_ADMINISTRATIONS = "select * from SubstanceAdministration inner join Act where Act_idAct=idAct";
    public static final String INSERT_CODE = "insert into SubstanceAdministration_has_Codes (SubstanceAdministration_idSubstanceAdministration,Codes_idCodes,type) values (?,?,?)";
    public static final String UPDATE_CODE = "update SubstanceAdministration_has_Codes set Codes_idCodes = ? where SubstanceAdministration_idSubstanceAdministration =? and type=?";
    public static final String DELETE_CODES = "delete from SubstanceAdministration_has_Codes where SubstanceAdministration_idSubstanceAdministration=?";
    public static final String SELECT_CODES = "select * from SubstanceAdministration_has_Codes inner join Codes where Codes_idCodes=idCodes and SubstanceAdministration_idSubstanceAdministration=?";

    // -----------------------------------------------------------------------------------------------------------------
    // CRUD methods
    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Insert a substance administration into the db
     * @param substanceAdministration a SubstanceAdministration object
     * @throws SubstanceAdministrationExistsException
     */
    @Override
    public void insertSubstanceAdministration(SubstanceAdministration substanceAdministration) throws SubstanceAdministrationExistsException, ActExistsException, CodeExistsException {
        try {
            SpringServices.getActDAO().insertAct(substanceAdministration);

            getJdbcTemplate().update(INSERT,
                    substanceAdministration.getIdSubstanceAdministration(),
                    substanceAdministration.getMaxDoseQuantity(),
                    substanceAdministration.getDoseCheckQuantity(),
                    substanceAdministration.getRateQuantity(),
                    substanceAdministration.getDoseQuantity(),
                    substanceAdministration.getId());
        }catch (DataIntegrityViolationException e){
            throw new SubstanceAdministrationExistsException(e);
        }
        try {
            getJdbcTemplate().update(INSERT_CODE,
                    substanceAdministration.getIdSubstanceAdministration(),
                    substanceAdministration.getRouteCode().getId(),
                    "routeCode");

            getJdbcTemplate().update(INSERT_CODE,
                    substanceAdministration.getIdSubstanceAdministration(),
                    substanceAdministration.getApproachSiteCode().getId(),
                    "approachSiteCode");

            getJdbcTemplate().update(INSERT_CODE,
                    substanceAdministration.getIdSubstanceAdministration(),
                    substanceAdministration.getAdministrationUnitCode().getId(),
                    "administrationUnitCode");
        }catch (DataIntegrityViolationException e){
            throw new CodeExistsException(e);
        }

    }

    /**
     * Update a substance administration from the db
     * @param substanceAdministration a SubstanceAdministration object
     * @throws SubstanceAdministrationExistsException
     * @throws SubstanceAdministrationNotExistsException
     */
    @Override
    public void updateSubstanceAdministration(SubstanceAdministration substanceAdministration) throws SubstanceAdministrationExistsException, SubstanceAdministrationNotExistsException, ActNotExistsException, ActExistsException, CodeIsNotValidException, CodeNotExistsException, CodeExistsException {
        if (isDifferent(substanceAdministration)){
            try {
                SpringServices.getActDAO().updateAct(substanceAdministration);

                int rows = getJdbcTemplate().update(UPDATE,
                        substanceAdministration.getMaxDoseQuantity(),
                        substanceAdministration.getDoseCheckQuantity(),
                        substanceAdministration.getRateQuantity(),
                        substanceAdministration.getDoseQuantity(),
                        substanceAdministration.getIdSubstanceAdministration());
                if (rows==0){
                    throw new SubstanceAdministrationNotExistsException();
                }
                updateCodes(substanceAdministration);

            }catch (DataIntegrityViolationException e){
                throw new SubstanceAdministrationExistsException(e);
            }
        }else {
            SpringServices.getActDAO().updateAct(substanceAdministration);
            updateCodes(substanceAdministration);
        }
    }

    /**
     * Delete a substance administration from the db
     * @param id the substance administration id
     * @throws SubstanceAdministrationNotExistsException
     * @throws SubstanceAdministrationCannotBeDeletedException
     */
    @Override
    public void deleteSubstanceAdministration(String id) throws SubstanceAdministrationNotExistsException, SubstanceAdministrationCannotBeDeletedException, ActNotExistsException, ActCannotBeDeleted, CodeIsNotValidException, CodeNotExistsException {
        try {

            getJdbcTemplate().update(DELETE_CODES,id);

            int rows = getJdbcTemplate().update(DELETE,id);
            if (rows==0){
                throw new SubstanceAdministrationNotExistsException();
            }

            SubstanceAdministration substanceAdministration = getSubstanceAdministration(id);
            SpringServices.getActDAO().deleteAct(substanceAdministration.getId());

        }catch (DataIntegrityViolationException e){
            throw new SubstanceAdministrationCannotBeDeletedException(e);
        }
    }

    /**
     * Get a substance administration from the db
     * @param id the substance administration id
     * @return substance administration
     * @throws SubstanceAdministrationNotExistsException
     */
    @Override
    public SubstanceAdministration getSubstanceAdministration(String id) throws SubstanceAdministrationNotExistsException, CodeNotExistsException, CodeIsNotValidException {
        try {
            SubstanceAdministration substanceAdministration =
                    getJdbcTemplate().queryForObject(SELECT_SUBSTANCE_ADMINISTRATION,
                            new SubstanceAdministrationRowMapper(), id);
            ActDAO actDAO = SpringServices.getActDAO();
            actDAO.setActCodes(substanceAdministration,actDAO.getActCodes(substanceAdministration.getId()));
            setSubstanceAdministrationCodes(substanceAdministration,getSubstanceAdministrationCodes(id));

            return substanceAdministration;

        }catch (EmptyResultDataAccessException e){
            throw new SubstanceAdministrationNotExistsException(e);
        }
    }

    /**
     * Get all the substance administrations from the db
     * @return substance administrations
     * @throws SubstanceAdministrationNotExistsException
     */
    @Override
    public List<SubstanceAdministration> getSubstanceAdministrations() throws SubstanceAdministrationNotExistsException, CodeNotExistsException, CodeIsNotValidException {
        try {
            List<SubstanceAdministration> substanceAdministrations =
                    getJdbcTemplate().query(SELECT_SUBSTANCE_ADMINISTRATIONS,
                            new SubstanceAdministrationRowMapper());

            for (SubstanceAdministration substanceAdministration:substanceAdministrations){
                ActDAO actDAO = SpringServices.getActDAO();
                actDAO.setActCodes(substanceAdministration,actDAO.getActCodes(substanceAdministration.getId()));
                setSubstanceAdministrationCodes(substanceAdministration,getSubstanceAdministrationCodes(
                        substanceAdministration.getIdSubstanceAdministration()));
            }

            return substanceAdministrations;

        }catch (EmptyResultDataAccessException e){
            throw new SubstanceAdministrationNotExistsException(e);
        }
    }

    /**
     * Get the SubstanceAdministration codes
     * @param id the substance administration id
     * @return a list with its codes
     * @throws CodeNotExistsException
     */
    public List<Code> getSubstanceAdministrationCodes(String id) throws CodeNotExistsException {
        try {
            List<Code> codes = getJdbcTemplate().query(SELECT_CODES, new CodeRowMapper(), id);
            return codes;

        }catch (EmptyResultDataAccessException e){
            throw new CodeNotExistsException(e);
        }
    }

    /**
     * Set the substance administration codes
     * @param substanceAdministration a SubstanceAdministration object
     * @param codes a list with codes
     * @throws CodeIsNotValidException
     */
    public void setSubstanceAdministrationCodes(SubstanceAdministration substanceAdministration,List<Code> codes) throws CodeIsNotValidException {
        try {
            for (Code code:codes){
                String type = code.getType();

                if (type.equalsIgnoreCase("routeCode")){
                    substanceAdministration.setRouteCode(code);
                }else if (type.equalsIgnoreCase("approachSiteCode")){
                    substanceAdministration.setApproachSiteCode(code);
                }else if (type.equalsIgnoreCase("administrationUnitCode")){
                    substanceAdministration.setAdministrationUnitCode(code);
                }
            }
        }catch (Exception e){
            throw new CodeIsNotValidException(e);
        }
    }

    /**
     * Evaluate if the new substance administration is different of the old substance administration
     * @param substanceAdministration a SubstanceAdministration object
     * @return true if the substance administration is different
     * @throws CodeIsNotValidException
     * @throws CodeNotExistsException
     * @throws SubstanceAdministrationNotExistsException
     */
    private boolean isDifferent(SubstanceAdministration substanceAdministration) throws CodeIsNotValidException, CodeNotExistsException, SubstanceAdministrationNotExistsException {
        SubstanceAdministration oldSubstanceAdministration = getSubstanceAdministration(substanceAdministration.getIdSubstanceAdministration());
        int differentAttrs =0;

        if (!substanceAdministration.getMaxDoseQuantity().equalsIgnoreCase(oldSubstanceAdministration.getMaxDoseQuantity()))
            differentAttrs++;
        if (!substanceAdministration.getDoseCheckQuantity().equalsIgnoreCase(oldSubstanceAdministration.getDoseCheckQuantity()))
            differentAttrs++;
        if (!substanceAdministration.getRateQuantity().equalsIgnoreCase(oldSubstanceAdministration.getRateQuantity()))
            differentAttrs++;
        if (!substanceAdministration.getDoseQuantity().equalsIgnoreCase(oldSubstanceAdministration.getDoseQuantity()))
            differentAttrs++;

        return differentAttrs != 0;
    }

    /**
     * Update the substance administration codes
     * @param substanceAdministration a SubstanceAdministration object
     * @throws CodeNotExistsException
     * @throws CodeExistsException
     */
    private void updateCodes(SubstanceAdministration substanceAdministration) throws CodeNotExistsException, CodeExistsException {
        List<Code> oldCodes = getSubstanceAdministrationCodes(substanceAdministration.getIdSubstanceAdministration());

        for (Code code:oldCodes){
            try {
                if (code.getType().equalsIgnoreCase("routeCode")){
                    if (!code.getId().equals(substanceAdministration.getRouteCode().getId())){
                        getJdbcTemplate().update(UPDATE_CODE,
                                substanceAdministration.getRouteCode().getId(),
                                substanceAdministration.getIdSubstanceAdministration(),
                                "routeCode");
                    }
                }else if (code.getType().equalsIgnoreCase("approachSiteCode")){
                    if (!code.getId().equals(substanceAdministration.getApproachSiteCode().getId())){
                        getJdbcTemplate().update(UPDATE_CODE,
                                substanceAdministration.getApproachSiteCode().getId(),
                                substanceAdministration.getIdSubstanceAdministration(),
                                "approachSiteCode");
                    }
                }else if (code.getType().equalsIgnoreCase("administrationUnitCode")){
                    if (!code.getId().equals(substanceAdministration.getAdministrationUnitCode().getId())){
                        getJdbcTemplate().update(UPDATE_CODE,
                                substanceAdministration.getAdministrationUnitCode().getId(),
                                substanceAdministration.getIdSubstanceAdministration(),
                                "administrationUnitCode");
                    }
                }
            }catch (DataIntegrityViolationException e){
                throw new CodeExistsException(e);
            }
        }
    }
}
