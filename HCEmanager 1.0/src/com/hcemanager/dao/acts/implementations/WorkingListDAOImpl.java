package com.hcemanager.dao.acts.implementations;

import com.hcemanager.dao.acts.interfaces.ActDAO;
import com.hcemanager.dao.acts.interfaces.WorkingListDAO;
import com.hcemanager.dao.acts.rowMappers.WorkingListRowMapper;
import com.hcemanager.dao.codes.CodeRowMapper;
import com.hcemanager.dao.springServices.SpringServices;
import com.hcemanager.exceptions.acts.*;
import com.hcemanager.exceptions.codes.CodeExistsException;
import com.hcemanager.exceptions.codes.CodeIsNotValidException;
import com.hcemanager.exceptions.codes.CodeNotExistsException;
import com.hcemanager.models.acts.WorkingList;
import com.hcemanager.models.codes.Code;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.util.List;

/**
 * @author daniel, esteban.
 */
public class WorkingListDAOImpl extends JdbcDaoSupport implements WorkingListDAO {

    // -----------------------------------------------------------------------------------------------------------------
    // SQL sentences
    // -----------------------------------------------------------------------------------------------------------------

    public static final String INSERT = "insert into WorkingList(idWorkingList,Act_idAct) values (?,?)";
    public static final String DELETE = "delete from WorkingList where idWorkingList=?";
    public static final String SELECT_WORKING_LIST = "select * from WorkingList inner join Act where Act_idAct=idAct and  idWorkingList=?";
    public static final String SELECT_WORKING_LISTS = "select * from WorkingList inner join Act where Act_idAct=idAct";
    public static final String INSERT_CODE = "insert into WorkingList_has_Codes (WorkingList_idWorkingList,Codes_idCodes,type ) values (?,?,?)";
    public static final String UPDATE_CODE = "update WorkingList_has_Codes set Codes_idCodes = ? where WorkingList_idWorkingList=? and type =? ";
    public static final String DELETE_CODES = "delete from WorkingList_has_Codes where WorkingList_idWorkingList=?";
    public static final String SELECT_CODES = "select * from WorkingList_has_Codes inner join Codes where Codes_idCodes=idCodes and WorkingList_idWorkingList=?";

    // -----------------------------------------------------------------------------------------------------------------
    // CRUD methods
    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Insert a working lis into the db
     * @param workingList a WorkingList object
     * @throws WorkingListExistsException
     */
    @Override
    public void insertWorkingLIst(WorkingList workingList) throws WorkingListExistsException, ActExistsException, CodeExistsException {
        try {
            SpringServices.getActDAO().insertAct(workingList);

            getJdbcTemplate().update(INSERT,
                    workingList.getIdWorkingList(),
                    workingList.getId());
        }catch (DataIntegrityViolationException e){
            throw new WorkingListExistsException(e);
        }
        try {
            getJdbcTemplate().update(INSERT_CODE,
                    workingList.getIdWorkingList(),
                    workingList.getOwnershipLevelCode().getId(),
                    "ownershipLevelCode");
        }catch (DataIntegrityViolationException e){
            throw new CodeExistsException(e);
        }
    }

    /**
     * Update a working list from the db
     * @param workingList a WorkingList object
     * @throws WorkingListExistsException
     * @throws WorkingListNotExistsException
     */
    @Override
    public void updateWorkingList(WorkingList workingList) throws WorkingListExistsException, WorkingListNotExistsException, ActNotExistsException, ActExistsException, CodeIsNotValidException, CodeNotExistsException, CodeExistsException {
       SpringServices.getActDAO().updateAct(workingList);
        updateCode(workingList);
    }

    /**
     * Delete a working list from the db
     * @param id the working list id
     * @throws WorkingListNotExistsException
     * @throws WorkingListCannotBeDeletedException
     */
    @Override
    public void deleteWorkingList(String id) throws WorkingListNotExistsException, WorkingListCannotBeDeletedException, ActNotExistsException, ActCannotBeDeleted, CodeIsNotValidException, CodeNotExistsException {
        try {

            getJdbcTemplate().update(DELETE_CODES,id);

            int rows = getJdbcTemplate().update(DELETE, id);
            if (rows==0){
                throw new WorkingListNotExistsException();
            }

            WorkingList workingList = getWorkingList(id);
            SpringServices.getActDAO().deleteAct(workingList.getId());

        }catch (DataIntegrityViolationException e){
            throw new WorkingListCannotBeDeletedException(e);
        }
    }

    /**
     * Get a WorkingList from the db
     * @param id the working list id
     * @return workingList
     * @throws WorkingListNotExistsException
     */
    @Override
    public WorkingList getWorkingList(String id) throws WorkingListNotExistsException, CodeNotExistsException, CodeIsNotValidException {
        try {
            WorkingList workingList = getJdbcTemplate().queryForObject(SELECT_WORKING_LIST,
                    new WorkingListRowMapper(), id);
            ActDAO actDAO = SpringServices.getActDAO();
            actDAO.setActCodes(workingList,actDAO.getActCodes(workingList.getId()));
            setWorkingListCodes(workingList,getWorkingLIstCodes(id));
            return workingList;

        }catch (EmptyResultDataAccessException e){
            throw new WorkingListNotExistsException(e);

        }
    }

    /**
     * Get all the working lists from the db
     * @return workingLists
     * @throws WorkingListNotExistsException
     */
    @Override
    public List<WorkingList> getWorkingLists() throws WorkingListNotExistsException, CodeNotExistsException, CodeIsNotValidException {
        try {
            List<WorkingList> workingLists = getJdbcTemplate().query(SELECT_WORKING_LISTS,
                    new WorkingListRowMapper());

            for (WorkingList workingList:workingLists){
                ActDAO actDAO = SpringServices.getActDAO();
                actDAO.setActCodes(workingList,actDAO.getActCodes(workingList.getId()));
                setWorkingListCodes(workingList,getWorkingLIstCodes(workingList.getIdWorkingList()));
            }

            return workingLists;

        }catch (EmptyResultDataAccessException e){
            throw new WorkingListNotExistsException(e);

        }
    }

    /**
     * Get the workingList codes
     * @param id the working list id
     * @return a list with its codes
     * @throws CodeNotExistsException
     */
    public List<Code> getWorkingLIstCodes(String id) throws CodeNotExistsException {
        try {
            List<Code> codes = getJdbcTemplate().query(SELECT_CODES, new CodeRowMapper(), id);
            return codes;
        }catch (EmptyResultDataAccessException e){
            throw new CodeNotExistsException(e);
        }
    }

    /**
     * Set the workingList codes
     * @param workingList a WorkingList object
     * @param codes a list with workingList codes
     * @throws CodeIsNotValidException
     */
    public void setWorkingListCodes(WorkingList workingList,List<Code>codes) throws CodeIsNotValidException {
        try {

            for (Code code:codes){
                String type=code.getType();

                if (type.equalsIgnoreCase("ownerShipLevelCode")){
                    workingList.setOwnershipLevelCode(code);
                }
            }

        }catch (Exception e){
            throw new CodeIsNotValidException(e);
        }
    }

    /**
     * Update working list codes
     * @param workingList a WorkingList object
     * @throws CodeNotExistsException
     * @throws CodeExistsException
     */
    private void updateCode(WorkingList workingList) throws CodeNotExistsException, CodeExistsException {
        List<Code> oldCodes = getWorkingLIstCodes(workingList.getIdWorkingList());

        for (Code code:oldCodes){
            if (code.getType().equalsIgnoreCase("ownershipLevelCode")){
                if (!code.getId().equals(workingList.getOwnershipLevelCode().getId())){
                    try {
                        getJdbcTemplate().update(UPDATE_CODE,
                                workingList.getOwnershipLevelCode().getId(),
                                workingList.getIdWorkingList(),
                                "ownershipLevelCode");
                    }catch (DataIntegrityViolationException e){
                        throw new CodeExistsException(e);
                    }
                }
            }
        }
    }
}
