package com.hcemanager.dao.roles.implementations;

import com.hcemanager.dao.codes.CodeRowMapper;
import com.hcemanager.dao.roles.interfaces.RoleDAO;
import com.hcemanager.dao.roles.rowMappers.RoleRowMapper;
import com.hcemanager.exceptions.codes.CodeNotExistsException;
import com.hcemanager.exceptions.roles.RoleCannotBeDeletedException;
import com.hcemanager.exceptions.roles.RoleExistsException;
import com.hcemanager.exceptions.roles.RoleNotExistsException;
import com.hcemanager.models.codes.Code;
import com.hcemanager.models.roles.Role;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.util.List;

/**
 * @author daniel, esteban.
 */
public class RoleDAOImpl extends JdbcDaoSupport implements RoleDAO {


    //------------------------------------------------------------------------------------------------------------------
    //SQL sentences
    //------------------------------------------------------------------------------------------------------------------

    public static final String INSERT = "insert into Role(idRole,negationInd,name,addr,telecom,effectiveTime,certificateText,quantity,positionNumber) values (?,?,?,?,?,?,?,?,?)";
    public static final String UPDATE = "update Role set negationInd=?, name=?, addr=?, telecom=?, effectiveTime=?, certificateText=?,quantity=?, positionNumber=? where idRole=?";
    public static final String DELETE = "delete from Role where idRole=?";
    public static final String SELECT_ROLE = "select * from Role where idRole=?";
    public static final String SELECT_ROLES = "select * from Role";
    public static final String INSERT_CODE = "insert into Role_has_Codes(Codes_idCodes,Role_idRole,type) values (?,?,?);";
    public static final String UPDATE_CODE = "update Role_has_Codes set Codes_idCodes=? where Role_idRole=? and type=?;";
    public static final String DELETE_CODE = "delete from Role_has_Codes where Role_idRole=?";
    public static final String SELECT_CODES = "select * from Role_has_Codes inner join Codes where Codes_idCodes=idCodes and Role_idRole=?";

    //------------------------------------------------------------------------------------------------------------------
    //CRUD methods
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Insert an Role.
     * @param role a Role object
     * @throws RoleExistsException
     */
    @Override
    public void insertRole(Role role) throws RoleExistsException {
        try {

            getJdbcTemplate().update(INSERT,
                    role.getId(),
                    role.isNegationInd(),
                    role.getName(),
                    role.getAddr(),
                    role.getTelecom(),
                    role.getEffectiveTime(),
                    role.getCertificateText(),
                    role.getQuantity(),
                    role.getPositionNumber());

            //Codes

            getJdbcTemplate().update(INSERT_CODE,
                    role.getClassCode().getId(),
                    role.getId(),
                    role.getClassCode().getType());

            getJdbcTemplate().update(INSERT_CODE,
                    role.getCode().getId(),
                    role.getId(),
                    role.getCode().getType());

            getJdbcTemplate().update(INSERT_CODE,
                    role.getConfidentialityCode().getId(),
                    role.getId(),
                    role.getConfidentialityCode().getType());

            getJdbcTemplate().update(INSERT_CODE,
                    role.getStatusCode().getId(),
                    role.getId(),
                    role.getStatusCode().getType());

        }catch (DataIntegrityViolationException e){
            throw new RoleExistsException(e);
        }
    }

    /**
     * Update an Role.
     * @param role a Role object
     * @throws com.hcemanager.exceptions.roles.RoleNotExistsException
     * @throws com.hcemanager.exceptions.roles.RoleExistsException
     */
    @Override
    public void updateRole(Role role) throws RoleNotExistsException, RoleExistsException, CodeNotExistsException {

        if (isDifferent(role)){

            try {

                int rows = getJdbcTemplate().update(UPDATE,
                        role.isNegationInd(),
                        role.getName(),
                        role.getAddr(),
                        role.getTelecom(),
                        role.getEffectiveTime(),
                        role.getCertificateText(),
                        role.getQuantity(),
                        role.getPositionNumber(),
                        role.getId());
                if (rows==0){
                    throw new RoleNotExistsException();
                }
                updateCodes(role);
            }catch (DataIntegrityViolationException e){
                throw new RoleExistsException(e);
            }
        }else {
            updateCodes(role);
        }


    }

    /**
     * Delete an Role.
     * @param id the Role id
     * @throws RoleNotExistsException
     * @throws RoleCannotBeDeletedException
     */
    @Override
    public void deleteRole(String id) throws RoleNotExistsException, RoleCannotBeDeletedException {
        try {

            getJdbcTemplate().update(DELETE_CODE,id);

            int rows = getJdbcTemplate().update(DELETE, id);

            if (rows==0){
                throw new RoleNotExistsException();
            }
        }catch (DataIntegrityViolationException e){
            throw new RoleCannotBeDeletedException(e);
        }
    }

    /**
     * Get an Role
     * @param id the Role id
     * @return Role
     * @throws RoleNotExistsException
     */
    @Override
    public Role getRole(String id) throws RoleNotExistsException, CodeNotExistsException {
        try {
            Role role = getJdbcTemplate().queryForObject(SELECT_ROLE, new RoleRowMapper(), id);
            setRoleCodes(role, getRoleCodes(id));
            return role;
        }catch (EmptyResultDataAccessException e){
            throw new RoleNotExistsException(e);
        }
    }

    /**
     * Get all Roles.
     * @return List<Role>
     * @throws RoleNotExistsException
     */
    @Override
    public List<Role> getRoles() throws RoleNotExistsException, CodeNotExistsException {
        try {
            List<Role> roles = getJdbcTemplate().query(SELECT_ROLES, new RoleRowMapper());
            //TODO:set codes
            for (Role role:roles){
                setRoleCodes(role, getRoleCodes(role.getId()));
                roles.set(roles.indexOf(role),role);
            }
            return roles;
        }catch (EmptyResultDataAccessException e){
            throw new RoleNotExistsException(e);
        }
    }

    /**
     * Get Codes of Role by id.
     * @param id
     * @return
     * @throws CodeNotExistsException
     */
    public List<Code> getRoleCodes(String id) throws CodeNotExistsException {
        try {
            List<Code> codes = getJdbcTemplate().query(SELECT_CODES, new CodeRowMapper(), id);
            return codes;

        }catch (EmptyResultDataAccessException e){
            throw new CodeNotExistsException(e);
        }
    }

    /**
     * Set codes to Role
     * @param role
     * @param codes
     * @return
     */
    @Override
    public void setRoleCodes(Role role, List<Code> codes){

        for (Code code:codes){

            String typeCode = code.getType();

            if (typeCode.equals("classCode")){
                role.setClassCode(code);
            }else if (typeCode.equals("code")){
                role.setCode(code);
            }else if (typeCode.equals("confidentialityCode")){
                role.setConfidentialityCode(code);
            }else if (typeCode.equals("statusCode")){
                role.setStatusCode(code);
            }

        }
    }

    private boolean isDifferent(Role role) throws RoleNotExistsException, CodeNotExistsException {
        Role oldRole = getRole(role.getId());

        int attrDifferent=0;

        if (!role.getQuantity().equals(oldRole.getQuantity())){
            attrDifferent++;
        }
        if (!role.getPositionNumber().equals(oldRole.getPositionNumber())){
            attrDifferent++;
        }
        if (!role.getAddr().equals(oldRole.getAddr())){
            attrDifferent++;
        }
        if (!role.getCertificateText().equals(oldRole.getCertificateText())){
            attrDifferent++;
        }
        if (!role.getName().equals(oldRole.getName())){
            attrDifferent++;
        }
        if (!role.getTelecom().equals(oldRole.getTelecom())){
            attrDifferent++;
        }
        if (!role.getEffectiveTime().equals(oldRole.getEffectiveTime())){
            attrDifferent++;
        }
        if (role.isNegationInd()!=oldRole.isNegationInd()){
            attrDifferent++;
        }

        return attrDifferent!=0;
    }

    private void updateCodes(Role role) throws CodeNotExistsException {
        List<Code> oldCodes = getRoleCodes(role.getId());

        for (Code code:oldCodes){
            if (code.getType().equalsIgnoreCase(role.getClassCode().getType())){
                if (!code.getId().equals(role.getClassCode().getId())){
                    getJdbcTemplate().update(UPDATE_CODE,
                            role.getClassCode().getId(),
                            role.getId(),
                            role.getClassCode().getType());
                }
            }else if (code.getType().equalsIgnoreCase(role.getCode().getType())){
                if (!code.getId().equals(role.getCode().getId())){
                    getJdbcTemplate().update(UPDATE_CODE,
                            role.getCode().getId(),
                            role.getId(),
                            role.getCode().getType());
                }
            }else if (code.getType().equalsIgnoreCase(role.getConfidentialityCode().getType())){
                if (!code.getId().equals(role.getConfidentialityCode().getId())){
                    getJdbcTemplate().update(UPDATE_CODE,
                            role.getConfidentialityCode().getId(),
                            role.getId(),
                            role.getConfidentialityCode().getType());
                }
            }else if (code.getType().equalsIgnoreCase(role.getStatusCode().getType())){
                if (!code.getId().equals(role.getStatusCode().getId())){
                    getJdbcTemplate().update(UPDATE_CODE,
                            role.getStatusCode().getId(),
                            role.getId(),
                            role.getStatusCode().getType());
                }
            }

        }

    }
}
