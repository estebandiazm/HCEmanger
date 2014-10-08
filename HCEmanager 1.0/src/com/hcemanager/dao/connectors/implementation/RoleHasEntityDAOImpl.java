
package com.hcemanager.dao.connectors.implementation;

import com.hcemanager.dao.connectors.interfaces.RoleHasEntityDAO;
import com.hcemanager.dao.connectors.rowMappers.RoleHasEntityRowMapper;
import com.hcemanager.exceptions.connectors.RoleHasEntityCannotBeDeletedException;
import com.hcemanager.exceptions.connectors.RoleHasEntityExistsException;
import com.hcemanager.exceptions.connectors.RoleHasEntityNotExistsException;
import com.hcemanager.models.connectors.RoleHasEntity;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.util.List;

/**
 * @author Daniel Bellon & Juan Diaz
 */
public class RoleHasEntityDAOImpl extends JdbcDaoSupport implements RoleHasEntityDAO {

    //------------------------------------------------------------------------------------------------------------------
    //SQL sentences
    //------------------------------------------------------------------------------------------------------------------
    public static final String INSERT = "insert into Role_has_Entity (Role_idRole,Entity_idEntity) values (?,?);";
    public static final String UPDATE_FROM_ENTITY = "update Role_has_Entity set Role_idRole=? where Entity_idEntity=?";
    public static final String UPDATE_FROM_ROLE = "update Role_has_Entity set Entity_idEntity=? where Role_idRole=?;";
    public static final String DELETE_FROM_ENTITY = "delete from Role_has_Entity where Entity_idEntity=?;";
    public static final String DELETE_FROM_ROLE = "delete from Role_has_Entity where Role_idRole=?";
    public static final String SELECT_FROM_ENTITY = "select * from Role_has_Entity where Entity_idEntity=?";
    public static final String SELECT_FROM_ROLE = "select * from Role_has_Entity where Role_idRole=?;";
    public static final String SELECT_ROLES_HAS_ENTITIES = "select * from Role_has_Entity;";

    //------------------------------------------------------------------------------------------------------------------
    //Methods override
    //------------------------------------------------------------------------------------------------------------------

    @Override
    public void insertRoleHasEntity(RoleHasEntity roleHasEntity) throws RoleHasEntityExistsException {
        try {

            getJdbcTemplate().update(INSERT,
                    roleHasEntity.getIdRole(),
                    roleHasEntity.getIdEntity());

        }catch (DataIntegrityViolationException e){
            throw new RoleHasEntityExistsException(e);
        }
    }

    @Override
    public void updateRoleHasEntityFromEntity(RoleHasEntity roleHasEntity) throws RoleHasEntityExistsException {
        try {

            getJdbcTemplate().update(UPDATE_FROM_ENTITY,
                    roleHasEntity.getIdRole(),
                    roleHasEntity.getIdEntity());

        }catch (DataIntegrityViolationException e){
            throw new RoleHasEntityExistsException(e);
        }
    }

    @Override
    public void updateRoleHasEntityFromRole(RoleHasEntity roleHasEntity) throws RoleHasEntityExistsException {
        try {
            getJdbcTemplate().update(UPDATE_FROM_ROLE,
                    roleHasEntity.getIdEntity(),
                    roleHasEntity.getIdRole());
        } catch (DataIntegrityViolationException e){
            throw new RoleHasEntityExistsException(e);
        }
    }

    @Override
    public void deleteRoleHasEntityFromEntity(String id) throws RoleHasEntityNotExistsException, RoleHasEntityCannotBeDeletedException {
        try {
            int rows = getJdbcTemplate().update(DELETE_FROM_ENTITY, id);
            if (rows==0){
                throw new RoleHasEntityNotExistsException();
            }
        } catch (DataIntegrityViolationException e){
            throw new RoleHasEntityCannotBeDeletedException(e);
        }
    }

    @Override
    public void deleteRoleHasEntityFromRole(String id) throws RoleHasEntityNotExistsException, RoleHasEntityCannotBeDeletedException {
        try {
            int rows = getJdbcTemplate().update(DELETE_FROM_ROLE, id);
            if (rows==0){
                throw new RoleHasEntityNotExistsException();
            }
        } catch (DataIntegrityViolationException e){
            throw new RoleHasEntityCannotBeDeletedException(e);
        }
    }

    @Override
    public List<RoleHasEntity> getRoleHasEntityFromEntity(String id) throws RoleHasEntityNotExistsException {
        try {
            List<RoleHasEntity> roleHasEntities = getJdbcTemplate().query(SELECT_FROM_ENTITY, new RoleHasEntityRowMapper(), id);
            return roleHasEntities;
        } catch (EmptyResultDataAccessException e){
            throw new RoleHasEntityNotExistsException(e);
        }
    }

    @Override
    public List<RoleHasEntity> getRoleHasEntityFromRole(String id) throws RoleHasEntityNotExistsException {
        try {
            List<RoleHasEntity> roleHasEntities = getJdbcTemplate().query(SELECT_FROM_ROLE, new RoleHasEntityRowMapper(), id);
            return roleHasEntities;
        } catch (EmptyResultDataAccessException e){
            throw new RoleHasEntityNotExistsException(e);
        }
    }

    @Override
    public List<RoleHasEntity> getRolesHasEntities() throws RoleHasEntityNotExistsException {
        try {
            List<RoleHasEntity> roleHasEntities = getJdbcTemplate().query(SELECT_ROLES_HAS_ENTITIES, new RoleHasEntityRowMapper());
            return roleHasEntities;
        } catch (EmptyResultDataAccessException e){
            throw new RoleHasEntityNotExistsException(e);
        }
    }
}
