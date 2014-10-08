package com.hcemanager.dao.roles.implementations;

import com.hcemanager.dao.roles.interfaces.LicensedEntityDAO;
import com.hcemanager.dao.roles.rowMappers.LicensedEntityRowMapper;
import com.hcemanager.dao.springServices.SpringServices;
import com.hcemanager.exceptions.codes.CodeNotExistsException;
import com.hcemanager.exceptions.roles.*;
import com.hcemanager.models.roles.LicensedEntity;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.util.List;

/**
 * @author daniel, esteban.
 */
public class LicensedEntityDAOImpl extends JdbcDaoSupport implements LicensedEntityDAO {

    //------------------------------------------------------------------------------------------------------------------
    //SQL sentences
    //------------------------------------------------------------------------------------------------------------------

    public static final String INSERT = "insert into LicensedEntity(idLicensedEntity,recertificationTime,Role_idRole) values (?,?,?)";
    public static final String UPDATE = "update LicensedEntity set recertificationTime=? where idLicensedEntity=?";
    public static final String DELETE = "delete from LicensedEntity where idLicensedEntity=?";
    public static final String SELECT_LICENSED_ENTITY = "select * from LicensedEntity inner join Role where Role_idRole=idRole and idLicensedEntity=?";
    public static final String SELECT_LICENSED_ENTITIES = "select * from LicensedEntity inner join Role where Role_idRole=idRole";



    //------------------------------------------------------------------------------------------------------------------
    //CRUD methods.
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Insert a LicensedEntity
     * @param licensedEntity a LicensedEntity object
     * @throws LicensedEntityExistsException
     */
    @Override
    public void insertLicensedEntity(LicensedEntity licensedEntity) throws LicensedEntityExistsException, RoleExistsException {
        try {

            SpringServices.getRoleDAO().insertRole(licensedEntity);

            getJdbcTemplate().update(INSERT,
                    licensedEntity.getIdLicensedEntity(),
                    licensedEntity.getRecertificationTime(),
                    licensedEntity.getId());

        }catch (DataIntegrityViolationException e){
            throw new LicensedEntityExistsException(e);
        }
    }

    /**
     * Update an LicensedEntity.
     * @param licensedEntity a LicensedEntity object
     * @throws LicensedEntityNotExistsException
     * @throws LicensedEntityExistsException
     */
    @Override
    public void updateLicensedEntity(LicensedEntity licensedEntity) throws LicensedEntityNotExistsException, LicensedEntityExistsException, RoleExistsException, RoleNotExistsException, CodeNotExistsException {

        if (isDifferent(licensedEntity)){
            try {

                SpringServices.getRoleDAO().updateRole(licensedEntity);

                int rows = getJdbcTemplate().update(UPDATE,
                        licensedEntity.getRecertificationTime(),
                        licensedEntity.getIdLicensedEntity());

                if (rows==0){
                    throw new LicensedEntityNotExistsException();
                }
            }catch (DataIntegrityViolationException e){
                throw new LicensedEntityExistsException(e);
            }

        }else{
            SpringServices.getRoleDAO().updateRole(licensedEntity);
        }

    }

    /**
     * Delete LicensedEntity.
     * @param id the licensedEntity id
     * @throws LicensedEntityNotExistsException
     * @throws LicensedEntityCannotBeDeletedException
     */
    @Override
    public void deleteLicensedEntity(String id) throws LicensedEntityNotExistsException, LicensedEntityCannotBeDeletedException, RoleNotExistsException, RoleCannotBeDeletedException, CodeNotExistsException {
        try {

            int rows = getJdbcTemplate().update(DELETE, id);
            if (rows==0){
                throw new LicensedEntityNotExistsException();
            }

            LicensedEntity licensedEntity = getLicensedEntity(id);
            SpringServices.getRoleDAO().deleteRole(licensedEntity.getId());

        }catch (DataIntegrityViolationException e){
            throw new LicensedEntityCannotBeDeletedException(e);
        }
    }

    /**
     * Get a LicensedEntity.
     * @param id the licensedEntity id
     * @return licensedEntity
     * @throws LicensedEntityNotExistsException
     */
    @Override
    public LicensedEntity getLicensedEntity(String id) throws LicensedEntityNotExistsException, CodeNotExistsException {
        try {
            LicensedEntity licensedEntity = getJdbcTemplate().queryForObject(SELECT_LICENSED_ENTITY, new LicensedEntityRowMapper(), id);
            SpringServices.getRoleDAO().setRoleCodes(licensedEntity, SpringServices.getRoleDAO().getRoleCodes(licensedEntity.getId()));
            return licensedEntity;
        }catch (EmptyResultDataAccessException e){
            throw new LicensedEntityNotExistsException(e);
        }
    }

    /**
     * Get all LicensedEntities.
     * @return List<licensedEntity>
     * @throws LicensedEntityNotExistsException
     */
    @Override
    public List<LicensedEntity> getLicensedEntities() throws LicensedEntityNotExistsException, CodeNotExistsException {
        try {
            List<LicensedEntity> licensedEntities = getJdbcTemplate().query(SELECT_LICENSED_ENTITIES, new LicensedEntityRowMapper());
            for (LicensedEntity licensedEntity: licensedEntities){
                SpringServices.getRoleDAO().setRoleCodes(licensedEntity, SpringServices.getRoleDAO().getRoleCodes(licensedEntity.getId()));
            }

            return licensedEntities;
        }catch (EmptyResultDataAccessException e){
            throw new LicensedEntityNotExistsException(e);
        }
    }

    private boolean isDifferent(LicensedEntity licensedEntity) throws CodeNotExistsException, LicensedEntityNotExistsException {
        LicensedEntity oldLicensedEntity = getLicensedEntity(licensedEntity.getIdLicensedEntity());

        int attrDifferent=0;

        if (!licensedEntity.getRecertificationTime().equals(oldLicensedEntity.getRecertificationTime())){
            attrDifferent++;
        }
        return attrDifferent!=0;
    }
}
