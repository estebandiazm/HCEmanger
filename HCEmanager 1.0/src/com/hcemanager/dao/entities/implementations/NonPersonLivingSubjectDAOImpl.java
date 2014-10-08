package com.hcemanager.dao.entities.implementations;

import com.hcemanager.dao.codes.CodeRowMapper;
import com.hcemanager.dao.entities.rowMappers.NonPersonLivingSubjectRowMapper;
import com.hcemanager.dao.entities.interfaces.NonPersonLivingSubjectDAO;
import com.hcemanager.dao.springServices.SpringServices;
import com.hcemanager.exceptions.codes.CodeExistsException;
import com.hcemanager.exceptions.codes.CodeNotExistsException;
import com.hcemanager.exceptions.entities.*;
import com.hcemanager.models.codes.Code;
import com.hcemanager.models.entities.NonPersonLivingSubject;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.util.List;

/**
 * @author daniel, juan.
 */
public class NonPersonLivingSubjectDAOImpl extends JdbcDaoSupport implements NonPersonLivingSubjectDAO {

    //------------------------------------------------------------------------------------------------------------------
    //SQL sentece constants.
    //------------------------------------------------------------------------------------------------------------------

    public static final String INSERT = "insert into NonPersonLivingSubject (idNonPersonLivingSubject,strainText, LivingSubject_idLivingSubject) values (?,?,?)";
    public static final String UPDATE = "update NonPersonLivingSubject set strainText=? where idNonPersonLivingSubject=?";
    public static final String DELETE = "delete from NonPersonLivingSubject where idNonPersonLivingSubject=?";
    public static final String SELECT_NON_PERSON_LIVING_SUBJECT = "select * from NonPersonLivingSubject inner join LivingSubject inner join Entity where LivingSubject_idLivingSubject=idLivingSubject and Entity_idEntity=idEntity and idNonPersonLivingSubject=?";
    public static final String SELECT_NON_PERSON_LIVING_SUBJECTS = "select * from NonPersonLivingSubject inner join LivingSubject inner join Entity where LivingSubject_idLivingSubject=idLivingSubject and Entity_idEntity=idEntity;";
    public static final String INSERT_CODE = "insert into NonPersonLivingSubject_has_Codes (Codes_idCodes,NonPersonLivingSubject_idNonPersonLivingSubject,type) values (?,?,?);";
    public static final String UPDATE_CODE = "update NonPersonLivingSubject_has_Codes set Codes_idCodes = ? where NonPersonLivingSubject_idNonPersonLivingSubject=? and type=?;";
    public static final String DELETE_CODE = "delete from NonPersonLivingSubject_has_Codes where NonPersonLivingSubject_idNonPersonLivingSubject=?";
    public static final String SELECT_CODES = "select * from NonPersonLivingSubject_has_Codes inner join Codes where Codes_idCodes=idCodes and NonPersonLivingSubject_idNonPersonLivingSubject=?";



    //------------------------------------------------------------------------------------------------------------------
    //CRUD methods
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Insert an NonPersonLivingSubject.
     * @param nonPersonLivingSubject
     */
    @Override
    public void insertNonPersonLivingSubject(NonPersonLivingSubject nonPersonLivingSubject) throws NonPersonLivingSubjectExistsException, LivingSubjectExistsException, EntityExistsException {
        try {

            SpringServices.getLivingSubjectDAO().insertLivingSubject(nonPersonLivingSubject);

            getJdbcTemplate().update(INSERT,
                    nonPersonLivingSubject.getIdNonPersonLivingSubject(),
                    nonPersonLivingSubject.getStrainText(),
                    nonPersonLivingSubject.getIdLivingSubject());
            
            //Codes

            getJdbcTemplate().update(INSERT_CODE,
                    nonPersonLivingSubject.getGenderStatusCode().getId(),
                    nonPersonLivingSubject.getIdNonPersonLivingSubject(),
                    nonPersonLivingSubject.getGenderStatusCode().getType());
            
        }catch (DataIntegrityViolationException e){
            throw new NonPersonLivingSubjectExistsException(e);
        }
    }

    /**
     * Update an NonPersonLivingSubject
     * @param nonPersonLivingSubject
     * @throws NonPersonLivingSubjectNotExistsException
     * @throws NonPersonLivingSubjectExistsException
     */
    @Override
    public void updateNonPersonLivingSubject(NonPersonLivingSubject nonPersonLivingSubject) throws NonPersonLivingSubjectNotExistsException, NonPersonLivingSubjectExistsException, LivingSubjectExistsException, EntityExistsException, LivingSubjectNotExistsException, EntityNotExistsException, CodeNotExistsException, CodeExistsException {

        if (isDifferent(nonPersonLivingSubject)){

            try {

                SpringServices.getLivingSubjectDAO().updateLivingSubject(nonPersonLivingSubject);

                int rows = getJdbcTemplate().update(UPDATE,
                        nonPersonLivingSubject.getStrainText(),
                        nonPersonLivingSubject.getIdNonPersonLivingSubject());
                if (rows==0){
                    throw new NonPersonLivingSubjectNotExistsException();
                }
                updateCodes(nonPersonLivingSubject);

            }catch (DataIntegrityViolationException e){
                throw new NonPersonLivingSubjectExistsException(e);
            }
        } else {
            SpringServices.getLivingSubjectDAO().updateLivingSubject(nonPersonLivingSubject);
            updateCodes(nonPersonLivingSubject);
        }

    }

    /**
     * Delete an NonPersonLivingSubject.
     * @param id
     * @throws NonPersonLivingSubjectNotExistsException
     * @throws NonPersonLivingSubjectCannotBeDeletedException
     */
    @Override
    public void deleteNonPersonLivingSubject(String id) throws NonPersonLivingSubjectNotExistsException, NonPersonLivingSubjectCannotBeDeletedException, LivingSubjectCannotBeDeletedException, EntityCannotBeDeletedException, LivingSubjectNotExistsException, EntityNotExistsException, CodeNotExistsException {
        try {
            NonPersonLivingSubject nonPersonLivingSubject = getNonPersonLivingSubject(id);

            getJdbcTemplate().update(DELETE_CODE,id);

            int rows = getJdbcTemplate().update(DELETE, id);
            if(rows==0){
                throw new NonPersonLivingSubjectNotExistsException();
            }

            SpringServices.getLivingSubjectDAO().deleteLivingSubject(nonPersonLivingSubject.getIdLivingSubject());

        }catch (DataIntegrityViolationException e){
            throw new NonPersonLivingSubjectCannotBeDeletedException(e);
        }
    }

    /**
     * Get a NonPersonLivingSubject.
     * @param id
     * @return NonPersonLivingSubject
     * @throws NonPersonLivingSubjectNotExistsException
     */
    @Override
    public NonPersonLivingSubject getNonPersonLivingSubject(String id) throws NonPersonLivingSubjectNotExistsException, CodeNotExistsException {
        try {
            NonPersonLivingSubject nonPersonLivingSubject = getJdbcTemplate().queryForObject(SELECT_NON_PERSON_LIVING_SUBJECT, new NonPersonLivingSubjectRowMapper(), id);

            setNonPersonLivingSubjectCodes(nonPersonLivingSubject, getNonPersonLivingSubjectCodes(id));

            return nonPersonLivingSubject;
        }catch (EmptyResultDataAccessException e){
            throw new NonPersonLivingSubjectNotExistsException(e);
        }
    }

    /**
     * Get all NonPersonLivingSubjects.
     * @return List<NonPersonLivingSubject>
     * @throws NonPersonLivingSubjectNotExistsException
     */
    @Override
    public List<NonPersonLivingSubject> getNonPersonLivingSubjects() throws NonPersonLivingSubjectNotExistsException, CodeNotExistsException {
        try {
            List<NonPersonLivingSubject> nonPersonLivingSubjects = getJdbcTemplate().query(SELECT_NON_PERSON_LIVING_SUBJECTS, new NonPersonLivingSubjectRowMapper());

            for (NonPersonLivingSubject nonPersonLivingSubject:nonPersonLivingSubjects){
                setNonPersonLivingSubjectCodes(nonPersonLivingSubject, getNonPersonLivingSubjectCodes(nonPersonLivingSubject.getIdNonPersonLivingSubject()));
                nonPersonLivingSubjects.set(nonPersonLivingSubjects.indexOf(nonPersonLivingSubject), nonPersonLivingSubject);
            }

            return nonPersonLivingSubjects;
        }catch (EmptyResultDataAccessException e){
            throw new NonPersonLivingSubjectNotExistsException(e);
        }
    }

    /**
     * Get Codes of an NonPersonLivingSubject
     * @param id
     * @return
     * @throws CodeNotExistsException
     */
    public List<Code> getNonPersonLivingSubjectCodes(String id) throws CodeNotExistsException {
        try {
            List<Code> codes = getJdbcTemplate().query(SELECT_CODES, new CodeRowMapper(), id);
            return codes;
        }catch (EmptyResultDataAccessException e){
            throw new CodeNotExistsException(e);
        }
    }

    /**
     * Set Codes to NonPersonLivingSubject
     * @param nonPersonLivingSubject
     * @param codes
     * @return
     */
    public void setNonPersonLivingSubjectCodes(NonPersonLivingSubject nonPersonLivingSubject, List<Code> codes) throws CodeNotExistsException {

        SpringServices.getLivingSubjectDAO().setLivingSubjectCodes(nonPersonLivingSubject,
                SpringServices.getLivingSubjectDAO().getLivingSubjectCodes(nonPersonLivingSubject.getIdLivingSubject()));

        for (Code code:codes){

            String typeCode = code.getType();

            if (typeCode.equals("genderStatusCode")){
                nonPersonLivingSubject.setGenderStatusCode(code);
            }
        }
    }

    private boolean isDifferent(NonPersonLivingSubject nonPersonLivingSubject) throws NonPersonLivingSubjectNotExistsException, CodeNotExistsException {
        NonPersonLivingSubject oldNonPersonLivingSubject = getNonPersonLivingSubject(nonPersonLivingSubject.getIdNonPersonLivingSubject());

        int attrDifferent = 0;

        if (!nonPersonLivingSubject.getStrainText().equals(oldNonPersonLivingSubject.getStrainText())){
            attrDifferent++;
        }

        return attrDifferent!=0;
    }

    private void updateCodes(NonPersonLivingSubject nonPersonLivingSubject) throws CodeNotExistsException, CodeExistsException {
        List<Code> oldCodes = getNonPersonLivingSubjectCodes(nonPersonLivingSubject.getIdNonPersonLivingSubject());

        for (Code code:oldCodes){
            try {
                if (code.getType().equalsIgnoreCase(nonPersonLivingSubject.getGenderStatusCode().getType())){
                    if (!code.getId().equals(nonPersonLivingSubject.getGenderStatusCode().getId())){
                        getJdbcTemplate().update(UPDATE_CODE,
                                nonPersonLivingSubject.getGenderStatusCode().getId(),
                                nonPersonLivingSubject.getIdNonPersonLivingSubject(),
                                nonPersonLivingSubject.getGenderStatusCode().getType());
                    }
                }
            }catch (DataIntegrityViolationException e){
                throw new CodeExistsException(e);
            }
        }
    }
}
