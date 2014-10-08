package com.hcemanager.dao.entities.interfaces;

import com.hcemanager.exceptions.codes.CodeExistsException;
import com.hcemanager.exceptions.codes.CodeNotExistsException;
import com.hcemanager.exceptions.entities.EntityCannotBeDeletedException;
import com.hcemanager.exceptions.entities.EntityExistsException;
import com.hcemanager.exceptions.entities.EntityNotExistsException;
import com.hcemanager.models.codes.Code;
import com.hcemanager.models.entities.Entity;

import java.util.List;

/**
 * @author daniel.
 */
public interface EntityDAO {

    public void insertEntity(Entity entity) throws EntityExistsException;
    public void updateEntity(Entity entity) throws EntityNotExistsException, EntityExistsException, CodeNotExistsException, CodeExistsException;
    public void deleteEntity(String id) throws EntityCannotBeDeletedException, EntityNotExistsException;
    public Entity getEntity(String id) throws EntityNotExistsException, CodeNotExistsException;
    public List<Entity> getEntities() throws EntityExistsException, EntityNotExistsException, CodeNotExistsException;
    public List<Code> getEntityCodes(String id) throws CodeNotExistsException;
    public void setEntityCodes(Entity entity, List<Code> codes);
}
