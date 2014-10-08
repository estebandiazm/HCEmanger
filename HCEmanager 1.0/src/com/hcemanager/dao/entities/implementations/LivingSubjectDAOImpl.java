package com.hcemanager.dao.entities.implementations;

import com.hcemanager.dao.codes.CodeRowMapper;
import com.hcemanager.dao.entities.rowMappers.LivingSubjectRowMapper;
import com.hcemanager.dao.entities.interfaces.LivingSubjectDAO;
import com.hcemanager.dao.springServices.SpringServices;
import com.hcemanager.exceptions.codes.CodeExistsException;
import com.hcemanager.exceptions.codes.CodeNotExistsException;
import com.hcemanager.exceptions.entities.*;
import com.hcemanager.models.codes.Code;
import com.hcemanager.models.entities.LivingSubject;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.util.List;

/**
 * @author daniel, juan.
 */
public class LivingSubjectDAOImpl extends JdbcDaoSupport implements LivingSubjectDAO {

    //------------------------------------------------------------------------------------------------------------------
    //SQL sentence constants
    //------------------------------------------------------------------------------------------------------------------

    public static final String INSERT = "insert into LivingSubject (idLivingSubject, birthTime, deceasedInd, deceasedTime, multipleBirthInd, multipleBirthOrderNumber, organDonorInd, Entity_idEntity) values (?,?,?,?,?,?,?,?)";
    public static final String UPDATE = "update LivingSubject set birthTime=?, deceasedInd=?, deceasedTime=?, multipleBirthInd=?, multipleBirthOrderNumber=?, organDonorInd=? where idLivingSubject=?";
    public static final String DELETE = "delete from LivingSubject where idLivingSubject=?";
    public static final String SELECT_LIVING_SUBJECT = "select * from LivingSubject inner join Entity where Entity_idEntity=idEntity and idLivingSubject=?";
    public static final String SELECT_LIVING_SUBJECTS = "select * from LivingSubject inner join Entity where Entity_idEntity=idEntity";
    public static final String INSERT_CODE = "insert into Codes_has_LivingSubject (Codes_idCodes,LivingSubject_idLivingSubject,type) values (?,?,?);";
    public static final String UPDATE_CODE = "update Codes_has_LivingSubject set Codes_idCodes=? where LivingSubject_idLivingSubject=? and type=?;";
    public static final String DELETE_CODE = "delete from Codes_has_LivingSubject where LivingSubject_idLivingSubject=?";
    public static final String SELECT_CODES = "select * from Codes_has_LivingSubject inner join Codes where Codes_idCodes=idCodes and LivingSubject_idLivingSubject=?";



    //------------------------------------------------------------------------------------------------------------------
    //CRUD methods
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Insert an LivingSubject.
     * @param livingSubject
     */
    @Override
    public void insertLivingSubject(LivingSubject livingSubject) throws LivingSubjectExistsException, EntityExistsException {

        try {

            SpringServices.getEntityDAO().insertEntity(livingSubject);

            getJdbcTemplate().update(INSERT,
                    livingSubject.getIdLivingSubject(),
                    livingSubject.getBirthTime(),
                    livingSubject.isDeceasedInd(),
                    livingSubject.getDeceasedTime(),
                    livingSubject.isMultipleBirthInd(),
                    livingSubject.getMultipleBirthOrderNumber(),
                    livingSubject.isOrganDonorInd(),
                    livingSubject.getId());
            
            //Codes
            
            getJdbcTemplate().update(INSERT_CODE,
                    livingSubject.getAdministrativeGenderCode().getId(),
                    livingSubject.getIdLivingSubject(),
                    livingSubject.getAdministrativeGenderCode().getType());

        }catch (DataIntegrityViolationException e){
            throw new LivingSubjectExistsException(e);
        }
    }

    /**
     * Update an LivingSubject.
     * @param livingSubject
     * @throws LivingSubjectExistsException
     * @throws LivingSubjectNotExistsException
     */
    @Override
    public void updateLivingSubject(LivingSubject livingSubject) throws LivingSubjectExistsException, LivingSubjectNotExistsException, EntityNotExistsException, EntityExistsException, CodeExistsException, CodeNotExistsException {

        if (isDifferent(livingSubject)){

            try {

                SpringServices.getEntityDAO().updateEntity(livingSubject);

                int rows = getJdbcTemplate().update(UPDATE,
                        livingSubject.getBirthTime(),
                        livingSubject.isDeceasedInd(),
                        livingSubject.getDeceasedTime(),
                        livingSubject.isMultipleBirthInd(),
                        livingSubject.getMultipleBirthOrderNumber(),
                        livingSubject.isOrganDonorInd(),
                        livingSubject.getIdLivingSubject());

                if (rows==0){
                    throw new LivingSubjectNotExistsException();
                }
                updateCodes(livingSubject);
            }catch (DataIntegrityViolationException e){
                throw new LivingSubjectExistsException(e);
            }
        }else {
            SpringServices.getEntityDAO().updateEntity(livingSubject);
            updateCodes(livingSubject);
        }

    }

    /**
     * Delete an LivingSubject.
     * @param id
     * @throws com.hcemanager.exceptions.entities.LivingSubjectCannotBeDeletedException
     * @throws LivingSubjectNotExistsException
     */
    @Override
    public void deleteLivingSubject(String id) throws LivingSubjectCannotBeDeletedException, LivingSubjectNotExistsException, EntityNotExistsException, EntityCannotBeDeletedException, CodeNotExistsException {
        try {
            LivingSubject livingSubject= getLivingSubject(id);

            getJdbcTemplate().update(DELETE_CODE, id);

            int rows = getJdbcTemplate().update(DELETE, id);
            if (rows==0){
                throw new LivingSubjectNotExistsException();
            }

            SpringServices.getEntityDAO().deleteEntity(livingSubject.getId());

        }catch (DataIntegrityViolationException e){
            throw new LivingSubjectCannotBeDeletedException(e);
        }
    }


    /**
     * Get an LivingSubject object.
     * @param id
     * @return LivingSubject
     * @throws LivingSubjectNotExistsException
     */
    @Override
    public LivingSubject getLivingSubject(String id) throws LivingSubjectNotExistsException, CodeNotExistsException {
        try {
            LivingSubject livingSubject = getJdbcTemplate().queryForObject(SELECT_LIVING_SUBJECT, new LivingSubjectRowMapper(), id);
            setLivingSubjectCodes(livingSubject, getLivingSubjectCodes(id));
            return livingSubject;
        }catch (EmptyResultDataAccessException e){
            throw new LivingSubjectNotExistsException(e);
        }
    }

    /**
     * Get all LivingSubjects.
     * @return List<LivingSubject>
     * @throws LivingSubjectNotExistsException
     */
    @Override
    public List<LivingSubject> getLivingSubjects() throws LivingSubjectNotExistsException, CodeNotExistsException {
        try {
            List<LivingSubject> livingSubjects = getJdbcTemplate().query(SELECT_LIVING_SUBJECTS, new LivingSubjectRowMapper());

            for (LivingSubject livingSubject:livingSubjects){
                setLivingSubjectCodes(livingSubject, getLivingSubjectCodes(livingSubject.getIdLivingSubject()));
                livingSubjects.set(livingSubjects.indexOf(livingSubject), livingSubject);
            }

            return livingSubjects;
        }catch (EmptyResultDataAccessException e){
            throw new LivingSubjectNotExistsException(e);
        }
    }

    /**
     * Get Codes of an LivingSubject by id
     * @param id
     * @return
     * @throws CodeNotExistsException
     */
    public List<Code> getLivingSubjectCodes(String id) throws CodeNotExistsException {
        try {
            List<Code> codes = getJdbcTemplate().query(SELECT_CODES, new CodeRowMapper(), id);
            return codes;
        }catch (EmptyResultDataAccessException e){
            throw new CodeNotExistsException(e);
        }
    }

    /**
     * Set Codes to LivingSubject
     * @param livingSubject
     * @param codes
     * @return
     */
    public void setLivingSubjectCodes(LivingSubject livingSubject, List<Code> codes) throws CodeNotExistsException {

        SpringServices.getEntityDAO().setEntityCodes(livingSubject, SpringServices.getEntityDAO().getEntityCodes(livingSubject.getId()));

        for (Code code:codes){

            String typeCode= code.getType();

            if (typeCode.equals("administrativeGenderCode")){
                livingSubject.setAdministrativeGenderCode(code);
            }

        }
    }

    private boolean isDifferent(LivingSubject livingSubject) throws LivingSubjectNotExistsException, CodeNotExistsException {

        LivingSubject oldLivingSubject = getLivingSubject(livingSubject.getIdLivingSubject());

        int attrDifferent = 0;

        if (livingSubject.isDeceasedInd()!=oldLivingSubject.isDeceasedInd()){
            attrDifferent++;
        }
        if (livingSubject.isMultipleBirthInd()!=oldLivingSubject.isMultipleBirthInd()){
            attrDifferent++;
        }
        if (livingSubject.isOrganDonorInd()!=oldLivingSubject.isOrganDonorInd()){
            attrDifferent++;
        }
        if (!livingSubject.getBirthTime().equals(oldLivingSubject.getBirthTime())){
            attrDifferent++;
        }
        if (!livingSubject.getDeceasedTime().equals(oldLivingSubject.getDeceasedTime())){
            attrDifferent++;
        }
        if (livingSubject.getMultipleBirthOrderNumber()!=oldLivingSubject.getMultipleBirthOrderNumber()){
            attrDifferent++;
        }

        return attrDifferent!=0;
    }

    private void updateCodes(LivingSubject livingSubject) throws CodeNotExistsException, CodeExistsException {
        List<Code> oldCodes = getLivingSubjectCodes(livingSubject.getIdLivingSubject());

        for (Code code:oldCodes){
            try {
                if (code.getType().equalsIgnoreCase(livingSubject.getAdministrativeGenderCode().getType())){
                    if (!code.getId().equals(livingSubject.getAdministrativeGenderCode().getId())){
                        getJdbcTemplate().update(UPDATE_CODE,
                                livingSubject.getAdministrativeGenderCode().getId(),
                                livingSubject.getIdLivingSubject(),
                                livingSubject.getAdministrativeGenderCode().getType());
                    }
                }

            }catch (DataIntegrityViolationException e){
                throw new CodeExistsException(e);
            }
        }
    }
}
