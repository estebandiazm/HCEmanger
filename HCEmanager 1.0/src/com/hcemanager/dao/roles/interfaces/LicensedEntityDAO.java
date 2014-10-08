package com.hcemanager.dao.roles.interfaces;

import com.hcemanager.exceptions.codes.CodeNotExistsException;
import com.hcemanager.exceptions.roles.*;
import com.hcemanager.models.roles.LicensedEntity;

import java.util.List;

/**
 * @author daniel, esteban.
 */
public interface LicensedEntityDAO {

    public void insertLicensedEntity(LicensedEntity licensedEntity) throws LicensedEntityExistsException, RoleExistsException;
    public void updateLicensedEntity(LicensedEntity licensedEntity) throws LicensedEntityNotExistsException, LicensedEntityExistsException, RoleExistsException, RoleNotExistsException, CodeNotExistsException;
    public void deleteLicensedEntity(String id) throws LicensedEntityNotExistsException, LicensedEntityCannotBeDeletedException, RoleNotExistsException, RoleCannotBeDeletedException, CodeNotExistsException;
    public LicensedEntity getLicensedEntity(String id) throws LicensedEntityNotExistsException, CodeNotExistsException;
    public List<LicensedEntity> getLicensedEntities() throws LicensedEntityNotExistsException, CodeNotExistsException;
}
