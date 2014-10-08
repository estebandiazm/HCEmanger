package com.hcemanager.dao.roles.implementations;

import com.hcemanager.dao.codes.CodeRowMapper;
import com.hcemanager.dao.roles.interfaces.AccessDAO;
import com.hcemanager.dao.roles.rowMappers.AccessRowMapper;
import com.hcemanager.dao.springServices.SpringServices;
import com.hcemanager.exceptions.codes.CodeExistsException;
import com.hcemanager.exceptions.codes.CodeNotExistsException;
import com.hcemanager.exceptions.roles.*;
import com.hcemanager.models.codes.Code;
import com.hcemanager.models.roles.Access;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.util.List;

/**
 * @author daniel, esteban.
 */
public class AccessDAOImpl extends JdbcDaoSupport implements AccessDAO {

    //------------------------------------------------------------------------------------------------------------------
    //SQL sentences
    //------------------------------------------------------------------------------------------------------------------

    public static final String INSERT = "insert into Access(idAccess,gaugeQuantity,Role_idRole) values (?,?,?)";
    public static final String UPDATE = "update Access set gaugeQuantity=? where idAccess=?";
    public static final String DELETE = "delete from Access where idAccess=?";
    public static final String SELECT_ACCESS = "select * from Access inner join Role where Role_idRole=idRole and idAccess=?";
    public static final String SELECT_ACCESSES = "select * from Access inner join Role where Role_idRole=idRole";
    public static final String INSERT_CODE = "insert into Access_has_Codes(Codes_idCodes,Access_idAccess,type) values (?,?,?);";
    public static final String UPDATE_CODE = "update Access_has_Codes set Codes_idCodes=? where Access_idAccess=? and type=?;";
    public static final String DELETE_CODE = "delete from Access_has_Codes where Access_idAccess=?";
    public static final String SELECT_CODES = "select * from Access_has_Codes inner join Codes where Codes_idCodes=idCodes and Access_idAccess=?";


    //------------------------------------------------------------------------------------------------------------------
    //CRUD methods
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Insert an Access.
     * @param access an Access object
     * @throws AccessExistsException
     */
    @Override
    public void insertAccess(Access access) throws AccessExistsException, RoleExistsException {
        try{

            SpringServices.getRoleDAO().insertRole(access);

            getJdbcTemplate().update(INSERT,
                    access.getIdAccess(),
                    access.getGaugeQuantity(),
                    access.getId());

            //Codes

            getJdbcTemplate().update(INSERT_CODE,
                    access.getApproachSiteCode().getId(),
                    access.getIdAccess(),
                    access.getApproachSiteCode().getType());

            getJdbcTemplate().update(INSERT_CODE,
                    access.getTargetSiteCode().getId(),
                    access.getIdAccess(),
                    access.getTargetSiteCode().getType());

        }catch (DataIntegrityViolationException e){
            throw new AccessExistsException(e);
        }
    }

    /**
     * Update an Access.
     * @param access an Access object
     * @throws AccessNotExistsException
     * @throws AccessExistsException
     */
    @Override
    public void updateAccess(Access access) throws AccessNotExistsException, AccessExistsException, RoleExistsException, RoleNotExistsException, CodeNotExistsException, CodeExistsException {

        if (isDefferent(access)){

            try{
                SpringServices.getRoleDAO().updateRole(access);

                int rows = getJdbcTemplate().update(UPDATE,
                        access.getGaugeQuantity(),
                        access.getIdAccess());

                if(rows==0){
                    throw  new AccessNotExistsException();
                }
                updateCodes(access);
            }catch (DataIntegrityViolationException e){
                throw new AccessExistsException(e);
            }
        }else {
            SpringServices.getRoleDAO().updateRole(access);
            updateCodes(access);
        }

    }

    /**
     * Delete an Access.
     * @param id the Access id
     * @throws AccessNotExistsException
     * @throws AccessCannotBeDeletedException
     */
    @Override
    public void deleteAccess(String id) throws AccessNotExistsException, AccessCannotBeDeletedException, RoleNotExistsException, CodeNotExistsException, RoleCannotBeDeletedException {
        try{

            getJdbcTemplate().update(DELETE_CODE,id);

            int rows = getJdbcTemplate().update(DELETE, id);
            if(rows==0){
                throw new AccessNotExistsException();
            }

            Access access = getAccess(id);
            SpringServices.getRoleDAO().deleteRole(access.getId());

        }catch (DataIntegrityViolationException e){
            throw new AccessCannotBeDeletedException(e);
        }
    }

    /**
     * Get an Access.
     * @param id the Access id
     * @return Access
     * @throws AccessNotExistsException
     */
    @Override
    public Access getAccess(String id) throws AccessNotExistsException, CodeNotExistsException {
        try {
            Access access = getJdbcTemplate().queryForObject(SELECT_ACCESS, new AccessRowMapper(), id);
            setAccessCodes(access, getAccessCodes(id));
            return access;
        }catch (EmptyResultDataAccessException e){
            throw new AccessNotExistsException(e);
        }
    }

    /**
     * Get all Access
     * @return
     * @throws AccessNotExistsException
     */
    @Override
    public List<Access> getAccesses() throws AccessNotExistsException, CodeNotExistsException {
        try {
            List<Access> accesses = getJdbcTemplate().query(SELECT_ACCESSES, new AccessRowMapper());
            for (Access access:accesses){

                setAccessCodes(access, getAccessCodes(access.getIdAccess()));
                accesses.set(accesses.indexOf(access),access);
            }
            return accesses;
        }catch (EmptyResultDataAccessException e){
            throw new AccessNotExistsException(e);
        }
    }

    /**
     * Get Codes of Access
     * @param id
     * @return
     * @throws CodeNotExistsException
     */
    @Override
    public List<Code> getAccessCodes(String id) throws CodeNotExistsException {
        try {
            List<Code> codes = getJdbcTemplate().query(SELECT_CODES, new CodeRowMapper(), id);
            return codes;
        }catch (EmptyResultDataAccessException e){
            throw new CodeNotExistsException(e);
        }
    }

    /**
     * Set Codes of Access
     * @param access
     * @param codes
     * @return
     */
    @Override
    public void setAccessCodes(Access access, List<Code> codes) throws CodeNotExistsException {

        SpringServices.getRoleDAO().setRoleCodes(access, SpringServices.getRoleDAO().getRoleCodes(access.getId()));

        for (Code code:codes){

            String typeCode = code.getType();

            if (typeCode.equals("approachSiteCode")){
                access.setApproachSiteCode(code);
            }else if (typeCode.equals("targetSiteCode")){
                access.setTargetSiteCode(code);
            }
        }

    }

    private boolean isDefferent(Access access) throws AccessNotExistsException, CodeNotExistsException {
        Access oldAccess = getAccess(access.getIdAccess());

        int attrDifferent=0;

        if (!access.getGaugeQuantity().equals(oldAccess.getGaugeQuantity())){
            attrDifferent++;
        }

        return attrDifferent!=0;
    }

    private void updateCodes(Access access) throws CodeNotExistsException, CodeExistsException {
        List<Code> oldCodes = getAccessCodes(access.getIdAccess());

        for (Code code:oldCodes){
            try {
                if (code.getType().equalsIgnoreCase(access.getApproachSiteCode().getType())){
                    if (!code.getId().equals(access.getApproachSiteCode().getId())){
                        getJdbcTemplate().update(UPDATE_CODE,
                                access.getApproachSiteCode().getId(),
                                access.getIdAccess(),
                                access.getApproachSiteCode().getType());
                    }
                }else if (code.getType().equalsIgnoreCase(access.getTargetSiteCode().getType())){
                    if (!code.getId().equals(access.getTargetSiteCode().getId())){
                        getJdbcTemplate().update(UPDATE_CODE,
                                access.getTargetSiteCode().getId(),
                                access.getIdAccess(),
                                access.getTargetSiteCode().getType());
                    }
                }
            }catch (DataIntegrityViolationException e){
                throw new CodeExistsException(e);
            }
        }
    }

}
