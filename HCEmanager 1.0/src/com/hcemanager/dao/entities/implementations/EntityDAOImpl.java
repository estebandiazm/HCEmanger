package com.hcemanager.dao.entities.implementations;

import com.hcemanager.dao.codes.CodeRowMapper;
import com.hcemanager.dao.entities.rowMappers.EntityRowMapper;
import com.hcemanager.dao.entities.interfaces.EntityDAO;
import com.hcemanager.exceptions.codes.CodeExistsException;
import com.hcemanager.exceptions.codes.CodeNotExistsException;
import com.hcemanager.exceptions.entities.EntityCannotBeDeletedException;
import com.hcemanager.exceptions.entities.EntityExistsException;
import com.hcemanager.exceptions.entities.EntityNotExistsException;
import com.hcemanager.models.codes.Code;
import com.hcemanager.models.entities.Entity;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import javax.xml.crypto.Data;
import java.util.List;

/**
 * @author daniel, juan.
 */
public class EntityDAOImpl extends JdbcDaoSupport implements EntityDAO {

    //------------------------------------------------------------------------------------------------------------------
    //SQL sentence constants.
    //------------------------------------------------------------------------------------------------------------------

    public static final String INSERT = "insert into Entity(idEntity, quantity, name, description, existenceTime, telecom) values (?,?,?,?,?,?);";
    public static final String UPDATE = "update Entity set quantity=?, name=?, description=?, existenceTime=?, telecom=? where idEntity=?";
    public static final String DELETE = "delete from Entity where idEntity=?";
    public static final String SELECT_ENTITY = "select * from Entity where idEntity=?";
    public static final String SELECT_ENTITIES = "select * from Entity";
    public static final String INSERT_CODE = "insert into Codes_has_Entity(Codes_idCodes,Entity_idEntity,type) values (?,?,?);";
    public static final String UPDATE_CODE = "update Codes_has_Entity set Codes_idCodes=? where Entity_idEntity=? and type=?;";
    public static final String DELETE_CODE = "delete from Codes_has_Entity where Entity_idEntity=? and type=?;";
    public static final String SELECT_CODES = "select * from Codes_has_Entity inner join Codes where Codes_idCodes=idCodes and Entity_idEntity=?;";


    //------------------------------------------------------------------------------------------------------------------
    //CRUD methods
    //------------------------------------------------------------------------------------------------------------------

    /**
     *Insert a Entity.
     * @param entity
     */
    @Override
    public void insertEntity(Entity entity) throws EntityExistsException {
        try{
            getJdbcTemplate().update(INSERT,
                    entity.getId(),
                    entity.getQuantity(),
                    entity.getName(),
                    entity.getDescription(),
                    entity.getExistenceTime(),
                    entity.getTelecom());

        }catch (DataIntegrityViolationException e){
            throw new EntityExistsException(e);
        }

        try {

            //codes

            getJdbcTemplate().update(INSERT_CODE,
                    entity.getClassCode().getId(),
                    entity.getId(),
                    entity.getClassCode().getType());

            getJdbcTemplate().update(INSERT_CODE,
                    entity.getDeterminerCode().getId(),
                    entity.getId(),
                    entity.getDeterminerCode().getType());

            getJdbcTemplate().update(INSERT_CODE,
                    entity.getCode().getId(),
                    entity.getId(),
                    entity.getCode().getType());

            getJdbcTemplate().update(INSERT_CODE,
                    entity.getStatusCode().getId(),
                    entity.getId(),
                    entity.getStatusCode().getType());

            getJdbcTemplate().update(INSERT_CODE,
                    entity.getRiskCode().getId(),
                    entity.getId(),
                    entity.getRiskCode().getType());

            getJdbcTemplate().update(INSERT_CODE,
                    entity.getHandlingCode().getId(),
                    entity.getId(),
                    entity.getHandlingCode().getType());

        }catch (DataIntegrityViolationException e){
            throw new EntityExistsException(e);
        }


    }

    /**
     * Update a Entity.
     * @param entity
     */
    @Override
    public void updateEntity(Entity entity) throws EntityNotExistsException, EntityExistsException, CodeNotExistsException, CodeExistsException {

        if (isDifferent(entity)){

            try {
                int rows = getJdbcTemplate().update(UPDATE,
                        entity.getQuantity(),
                        entity.getName(),
                        entity.getDescription(),
                        entity.getExistenceTime(),
                        entity.getTelecom(),
                        entity.getId());

                if(rows==0){
                    throw new EntityNotExistsException();
                }
                updateCodes(entity);
            }catch (DataIntegrityViolationException e){
                throw new EntityExistsException(e);
            }
        }else{
            updateCodes(entity);
        }
    }

    /**
     * Delete a Entity.
     * @param id
     */
    @Override
    public void deleteEntity(String id) throws EntityCannotBeDeletedException, EntityNotExistsException {
        try{

            getJdbcTemplate().update(DELETE_CODE, id);

            int rows = getJdbcTemplate().update(DELETE, id);
            if (rows==0){
                throw new EntityNotExistsException();
            }
        }catch (DataIntegrityViolationException e){
            throw new EntityCannotBeDeletedException(e);
        }
    }


    /**
     * Get a Entity.
     * @param id
     * @return Entity Object
     * @throws EntityNotExistsException
     */
    @Override
    public Entity getEntity(String id) throws EntityNotExistsException, CodeNotExistsException {
        try {
            Entity entity = getJdbcTemplate().queryForObject(SELECT_ENTITY, new EntityRowMapper(),id);
            setEntityCodes(entity, getEntityCodes(id));

            return entity;
        }catch (EmptyResultDataAccessException e){
            throw new EntityNotExistsException(e);
        }
    }

    /**
     * Get all Entities
     * @return List<Entity>
     * @throws EntityExistsException
     */
    @Override
    public List<Entity> getEntities() throws EntityNotExistsException, CodeNotExistsException {

        try {
            List<Entity> entities = getJdbcTemplate().query(SELECT_ENTITIES, new EntityRowMapper());

            for (Entity entity:entities){
                setEntityCodes(entity, getEntityCodes(entity.getId()));
                entities.set(entities.indexOf(entity), entity);
            }

            return entities;
        }catch (EmptyResultDataAccessException e){
            throw new EntityNotExistsException(e);
        }
    }

    /**
     * Get Codes of an Entity by id.
     * @param id
     * @return
     * @throws CodeNotExistsException
     */
    @Override
    public List<Code> getEntityCodes(String id) throws CodeNotExistsException {
        try {
            List<Code> codes = getJdbcTemplate().query(SELECT_CODES, new CodeRowMapper(), id);
            return codes;
        }catch (EmptyResultDataAccessException e){
            throw new CodeNotExistsException(e);
        }
    }

    /**
     * Set Codes to Entity
     * @param entity
     * @param codes
     * @return
     */
    @Override
    public void setEntityCodes(Entity entity, List<Code> codes){

        for (Code code:codes){

            String typeCode = code.getType();

            if (typeCode.equals("classCode")){
                entity.setClassCode(code);
            }else if (typeCode.equals("determinerCode")){
                entity.setDeterminerCode(code);
            }else if (typeCode.equals("code")){
                entity.setCode(code);
            }else if (typeCode.equals("statusCode")){
                entity.setStatusCode(code);
            }else if (typeCode.equals("riskCode")){
                entity.setRiskCode(code);
            }else if (typeCode.equals("handlingCode")){
                entity.setHandlingCode(code);
            }
        }
    }

    /**
     * Compare the new Entity with old Entity
     * @param entity
     * @return
     * @throws EntityNotExistsException
     * @throws CodeNotExistsException
     */
    private boolean isDifferent(Entity entity) throws EntityNotExistsException, CodeNotExistsException {

        Entity oldEntity= getEntity(entity.getId());

        int attrDifferent=0;

        if (!entity.getName().equals(oldEntity.getName()))
            attrDifferent++;
        if (!entity.getQuantity().equals(oldEntity.getQuantity()))
            attrDifferent++;
        if (!entity.getDescription().equals(oldEntity.getDescription()))
            attrDifferent++;
        if (!entity.getExistenceTime().equals(oldEntity.getExistenceTime()))
            attrDifferent++;
        if (!entity.getTelecom().equals(oldEntity.getTelecom()))
            attrDifferent++;

        return attrDifferent!=0;

    }

    private void updateCodes(Entity entity) throws CodeNotExistsException, CodeExistsException {
        List<Code> oldCodes = getEntityCodes(entity.getId());

        for (Code code:oldCodes){
            try {
                if (code.getType().equalsIgnoreCase("classCode")){
                    if (!code.getId().equals(entity.getClassCode().getId())){
                        getJdbcTemplate().update(UPDATE_CODE,entity.getClassCode().getId(),entity.getId(),"classCode");
                    }
                }else if (code.getType().equalsIgnoreCase("determinerCode")){
                    if (!code.getId().equals(entity.getDeterminerCode().getId())){
                        getJdbcTemplate().update(UPDATE_CODE,entity.getDeterminerCode().getId(),entity.getId(),"determinerCode");
                    }
                }else if (code.getType().equalsIgnoreCase("code")){
                    if (!code.getId().equals(entity.getCode().getId())){
                        getJdbcTemplate().update(UPDATE_CODE, entity.getCode().getId(),entity.getId(),"code");
                    }
                }else if (code.getType().equalsIgnoreCase("statusCode")){
                    if (!code.getId().equals(entity.getStatusCode().getId())){
                        getJdbcTemplate().update(UPDATE_CODE,entity.getStatusCode().getId(),entity.getId(),"statusCode");
                    }
                }else if (code.getType().equalsIgnoreCase("riskCode")){
                    if (!code.getId().equals(entity.getRiskCode().getId())){
                        getJdbcTemplate().update(UPDATE_CODE, entity.getRiskCode().getId(),entity.getId(),"riskCode");
                    }
                }else if (code.getType().equalsIgnoreCase("handlingCode")){
                    if (!code.getId().equals(entity.getHandlingCode().getId())){
                        getJdbcTemplate().update(UPDATE_CODE, entity.getHandlingCode().getId(),entity.getId(),"handlingCode");
                    }
                }
            }catch (DataIntegrityViolationException e){
                throw  new CodeExistsException(e);
            }

        }
    }
}
