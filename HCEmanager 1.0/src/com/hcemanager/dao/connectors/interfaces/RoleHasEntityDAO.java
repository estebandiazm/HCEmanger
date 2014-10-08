package com.hcemanager.dao.connectors.interfaces;

import com.hcemanager.exceptions.connectors.RoleHasEntityCannotBeDeletedException;
import com.hcemanager.exceptions.connectors.RoleHasEntityExistsException;
import com.hcemanager.exceptions.connectors.RoleHasEntityNotExistsException;
import com.hcemanager.models.connectors.RoleHasEntity;

import java.util.List;

public interface RoleHasEntityDAO {

    public void insertRoleHasEntity(RoleHasEntity roleHasEntity) throws RoleHasEntityExistsException;

    public void updateRoleHasEntityFromEntity(RoleHasEntity roleHasEntity) throws RoleHasEntityExistsException;

    public void updateRoleHasEntityFromRole(RoleHasEntity roleHasEntity) throws RoleHasEntityExistsException;

    public void deleteRoleHasEntityFromEntity(String id) throws RoleHasEntityNotExistsException, RoleHasEntityCannotBeDeletedException;

    public void deleteRoleHasEntityFromRole(String id) throws RoleHasEntityNotExistsException, RoleHasEntityCannotBeDeletedException;

    public List<RoleHasEntity> getRoleHasEntityFromEntity(String id) throws RoleHasEntityNotExistsException;

    public List<RoleHasEntity> getRoleHasEntityFromRole(String id) throws RoleHasEntityNotExistsException;

    public List<RoleHasEntity> getRolesHasEntities() throws RoleHasEntityNotExistsException;
}
