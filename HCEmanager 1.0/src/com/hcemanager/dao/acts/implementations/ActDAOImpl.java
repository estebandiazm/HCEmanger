package com.hcemanager.dao.acts.implementations;

import com.hcemanager.dao.acts.interfaces.ActDAO;
import com.hcemanager.dao.acts.rowMappers.ActRowMapper;
import com.hcemanager.dao.codes.CodeRowMapper;
import com.hcemanager.exceptions.acts.ActCannotBeDeleted;
import com.hcemanager.exceptions.acts.ActExistsException;
import com.hcemanager.exceptions.acts.ActNotExistsException;
import com.hcemanager.exceptions.codes.CodeExistsException;
import com.hcemanager.exceptions.codes.CodeIsNotValidException;
import com.hcemanager.exceptions.codes.CodeNotExistsException;
import com.hcemanager.models.acts.Act;
import com.hcemanager.models.codes.Code;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.util.List;

/**
 * This class implements the ActDAO interface an implements its methods whit the correct sql syntax
 * @author daniel.
 */
public class ActDAOImpl  extends JdbcDaoSupport implements ActDAO {

    //------------------------------------------------------------------------------------------------------------------
    //SQL sentence constants
    //------------------------------------------------------------------------------------------------------------------

    public static final String INSERT = "insert into Act(independentInd,interruptibleInd,negationInd,effectiveTime,activityTime,availabilityTime,repeatNumber,derivationExpr,idAct,text,title)values(?,?,?,?,?,?,?,?,?,?,?) ";
    public static final String UPDATE = "update Act set independentInd=?,interruptibleInd=?,negationInd=?,effectiveTime=?,activityTime=?,availabilityTime=?,repeatNumber=?,derivationExpr=?,text=?,title=? where idAct=?";
    public static final String DELETE = "delete from Act where idAct=?";
    public static final String SELECT_ACT = "select * from Act where idAct=?";
    public static final String SELECT_ACTS = "select * from Act";
    public static final String INSERT_CODE = "insert into Act_has_Codes(Act_idAct,Codes_idCodes,type) values (?,?,?)";
    public static final String UPDATE_CODE = "update Act_has_Codes set Codes_idCodes = ? where Act_idAct=? and type=?";
    public static final String DELETE_CODES = "delete from Act_has_Codes where Act_idAct =?";
    public static final String SELECT_CODES = "select * from Act_has_Codes inner join Codes where Codes_idCodes=idCodes and Act_idAct=?";

    //------------------------------------------------------------------------------------------------------------------
    //CRUD methods
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Insert an act ina db
     * @param act an Act object
     * @throws ActExistsException
     */
    @Override
    public void insertAct(Act act) throws ActExistsException, CodeExistsException {

        try {

            getJdbcTemplate().update(INSERT,
                    act.isIndependentInd(),
                    act.isInterruptibleInd(),
                    act.isNegationInd(),
                    act.getEffectiveTime(),
                    act.getActivityTime(),
                    act.getAvailabilityTime(),
                    act.getRepeatNumber(),
                    act.getDerivationExpr(),
                    act.getId(),
                    act.getText(),
                    act.getTitle());


        }catch (DataIntegrityViolationException e){
            throw new ActExistsException(e);

        }
        try {
            getJdbcTemplate().update(INSERT_CODE,act.getId(),act.getClassCode().getId(),"classCode");
            getJdbcTemplate().update(INSERT_CODE,act.getId(),act.getConfidentialityCode().getId(),"confidentialityCode");
            getJdbcTemplate().update(INSERT_CODE,act.getId(),act.getLanguageCode().getId(),"languageCode");
            getJdbcTemplate().update(INSERT_CODE,act.getId(),act.getLevelCode().getId(),"levelCode");
            getJdbcTemplate().update(INSERT_CODE,act.getId(),act.getModeCode().getId(),"modeCode");
            getJdbcTemplate().update(INSERT_CODE,act.getId(),act.getPriorityCode().getId(),"priorityCode");
            getJdbcTemplate().update(INSERT_CODE,act.getId(),act.getCode().getId(),"code");
            getJdbcTemplate().update(INSERT_CODE,act.getId(),act.getReasonCode().getId(),"reasonCode");
            getJdbcTemplate().update(INSERT_CODE,act.getId(),act.getStatusCode().getId(),"statusCode");
            getJdbcTemplate().update(INSERT_CODE,act.getId(),act.getUncertaintyCode().getId(),"uncertaintyCode");
        }catch (DataIntegrityViolationException e){
            throw new CodeExistsException(e);
        }
    }

    /**
     * Update an act in the db
     * @param act an Act object
     * @throws ActNotExistsException
     * @throws ActExistsException
     */
    @Override
    public void updateAct(Act act) throws ActNotExistsException, ActExistsException, CodeNotExistsException, CodeIsNotValidException, CodeExistsException {

        if (isDifferent(act)){
            try {

                int rows = getJdbcTemplate().update(UPDATE,
                        act.isIndependentInd(),
                        act.isInterruptibleInd(),
                        act.isNegationInd(),
                        act.getEffectiveTime(),
                        act.getActivityTime(),
                        act.getAvailabilityTime(),
                        act.getRepeatNumber(),
                        act.getDerivationExpr(),
                        act.getText(),
                        act.getTitle(),
                        act.getId());

                if (rows==0){
                    throw new ActNotExistsException();
                }

                updateCodes(act);

            }catch (DataIntegrityViolationException e){
                throw new ActExistsException(e);
            }
        }else {
            updateCodes(act);
        }
    }

    /**
     * Delete an act from the db
     * @param id the Act id
     * @throws ActNotExistsException
     * @throws ActCannotBeDeleted
     */
    @Override
    public void deleteAct(String id) throws ActNotExistsException, ActCannotBeDeleted {
        try {

            getJdbcTemplate().update(DELETE_CODES,id);

            int rows = getJdbcTemplate().update(DELETE, id);
            if (rows==0){
                throw new ActNotExistsException();
            }

        }catch(DataIntegrityViolationException e){
            throw new ActCannotBeDeleted(e);
        }
    }

    /**
     * Get an act from the db by its id
     * @param id the Act id
     * @return act
     * @throws ActNotExistsException
     */
    @Override
    public Act getAct(String id) throws ActNotExistsException, CodeNotExistsException, CodeIsNotValidException {
        try {
            Act act = getJdbcTemplate().queryForObject(SELECT_ACT,new ActRowMapper(), id);
            setActCodes(act,getActCodes(id));
            return act;


        }catch (EmptyResultDataAccessException e){
            throw new ActNotExistsException(e);
        }
    }

    /**
     * Get all acts in the db
     * @return acts
     * @throws ActNotExistsException
     */
    @Override
    public List<Act> getActs() throws ActNotExistsException {
        try {
            List<Act> acts = getJdbcTemplate().query(SELECT_ACTS, new ActRowMapper());
            return acts;

        }catch (EmptyResultDataAccessException e){
            throw new ActNotExistsException(e);
        }
    }

    /**
     * Get the act codes from the db
     * @param id the Act id
     * @return a list of codes
     * @throws CodeNotExistsException
     */
    @Override
    public List<Code> getActCodes(String id) throws CodeNotExistsException {
        try {
            List<Code> codes = getJdbcTemplate().query(SELECT_CODES, new CodeRowMapper(), id);
            return  codes;
        }catch (EmptyResultDataAccessException e){
            throw new CodeNotExistsException(e);

        }
    }

    /**
     * Set codes to an Act
     * @param act an Act object
     * @param codes the act codes
     * @throws CodeIsNotValidException
     */
    public void setActCodes(Act act, List<Code> codes) throws CodeIsNotValidException {

       try {

           for (Code code:codes){
               String typeCode = code.getType();

               if (typeCode.equalsIgnoreCase("classCode")){
                   act.setClassCode(code);
               }else if (typeCode.equalsIgnoreCase("code")){
                   act.setCode(code);
               }else if (typeCode.equalsIgnoreCase("confidentialityCode")){
                   act.setConfidentialityCode(code);
               }else if (typeCode.equalsIgnoreCase("languageCode")){
                   act.setLanguageCode(code);
               }else if (typeCode.equalsIgnoreCase("levelCode")){
                   act.setLevelCode(code);
               }else if(typeCode.equalsIgnoreCase("modeCode")){
                   act.setModeCode(code);
               }else if (typeCode.equalsIgnoreCase("priorityCode")){
                   act.setPriorityCode(code);
               }else if (typeCode.equalsIgnoreCase("reasonCode")){
                   act.setReasonCode(code);
               }else if (typeCode.equalsIgnoreCase("statusCode")){
                   act.setStatusCode(code);
               }else if (typeCode.equalsIgnoreCase("uncertaintyCode")){
                   act.setUncertaintyCode(code);
               }
           }

       }catch (Exception e){
           throw new CodeIsNotValidException(e);
       }

    }

    /**
     *
     * @param act an Act object
     * @return if the act is different
     * @throws CodeIsNotValidException
     * @throws ActNotExistsException
     * @throws CodeNotExistsException
     */
    private boolean isDifferent(Act act) throws CodeIsNotValidException, ActNotExistsException, CodeNotExistsException {

        Act oldAct= getAct(act.getId());
        int differentAttrs=0;

        if (act.isIndependentInd()!= oldAct.isIndependentInd())
            differentAttrs++;
        if (act.isInterruptibleInd()!=oldAct.isInterruptibleInd())
            differentAttrs++;
        if (act.isNegationInd()!=oldAct.isNegationInd())
            differentAttrs++;
        if (!act.getEffectiveTime().equalsIgnoreCase(oldAct.getEffectiveTime()))
            differentAttrs++;
        if (!act.getActivityTime().equalsIgnoreCase(oldAct.getActivityTime()))
            differentAttrs++;
        if (!act.getAvailabilityTime().equalsIgnoreCase(oldAct.getAvailabilityTime()))
            differentAttrs++;
        if (!act.getRepeatNumber().equalsIgnoreCase(oldAct.getRepeatNumber()))
            differentAttrs++;
        if (!act.getDerivationExpr().equalsIgnoreCase(oldAct.getDerivationExpr()))
            differentAttrs++;
        if (!act.getText().equalsIgnoreCase(oldAct.getText()))
            differentAttrs++;
        if (!act.getTitle().equalsIgnoreCase(oldAct.getTitle()))
            differentAttrs++;

        return differentAttrs!=0;
    }

    /**
     * Update the act codes
     * @param act an Act object
     * @throws CodeNotExistsException
     * @throws CodeExistsException
     */
    private void updateCodes(Act act) throws CodeNotExistsException, CodeExistsException {
        List<Code> oldCodes = getActCodes(act.getId());

        for (Code code:oldCodes){

            try {
                if (code.getType().equalsIgnoreCase("classCode")){
                    if (!code.getId().equals(act.getClassCode().getId())){
                        getJdbcTemplate().update(UPDATE_CODE,act.getClassCode().getId(),act.getId(),"classCode");
                    }
                }else if (code.getType().equalsIgnoreCase("code")){
                    if (!code.getId().equals(act.getCode().getId())){
                        getJdbcTemplate().update(UPDATE_CODE,act.getCode().getId(),act.getId(),"code");
                    }
                }else if (code.getType().equalsIgnoreCase("confidentialityCode")){
                    if (!code.getId().equals(act.getConfidentialityCode().getId())){
                        getJdbcTemplate().update(UPDATE_CODE,act.getConfidentialityCode().getId(),act.getId(),"confidentialityCode");
                    }
                }else if (code.getType().equalsIgnoreCase("languageCode")){
                    if (!code.getId().equals(act.getLanguageCode().getId())){
                        getJdbcTemplate().update(UPDATE_CODE,act.getLanguageCode().getId(),act.getId(),"languageCode");
                    }
                }else if (code.getType().equalsIgnoreCase("levelCode")){
                    if (!code.getId().equals(act.getLevelCode().getId())){
                        getJdbcTemplate().update(UPDATE_CODE,act.getLevelCode().getId(),act.getId(),"levelCode");
                    }
                }else if (code.getType().equalsIgnoreCase("modeCode")){
                    if (!code.getId().equals(act.getModeCode().getId())){
                        getJdbcTemplate().update(UPDATE_CODE,act.getModeCode().getId(),act.getId(),"modelCode");
                    }
                }else if (code.getType().equalsIgnoreCase("priorityCode")){
                    if (!code.getId().equals(act.getPriorityCode().getId())){
                        getJdbcTemplate().update(UPDATE_CODE,act.getPriorityCode().getId(),act.getId(),"priorityCode");
                    }
                }else if (code.getType().equalsIgnoreCase("reasonCode")){
                    if (!code.getId().equals(act.getReasonCode().getId())){
                        getJdbcTemplate().update(UPDATE_CODE,act.getReasonCode().getId(),act.getId(),"reasonCode");
                    }
                }else if (code.getType().equalsIgnoreCase("statusCode")){
                    if (!code.getId().equals(act.getStatusCode().getId())){
                        getJdbcTemplate().update(UPDATE_CODE,act.getStatusCode().getId(),act.getId(),"StatusCode");
                    }
                }else if (code.getType().equalsIgnoreCase("uncertaintyCode")){
                    if (!code.getId().equals(act.getUncertaintyCode().getId())){
                        getJdbcTemplate().update(UPDATE_CODE,act.getUncertaintyCode().getId(),act.getId(),"uncertaintyCode");
                    }
                }
            }catch (DataIntegrityViolationException e){
                throw new CodeExistsException(e);
            }
        }
    }

}
