package com.hcemanager.dao.entities.interfaces;

import com.hcemanager.exceptions.codes.CodeExistsException;
import com.hcemanager.exceptions.codes.CodeNotExistsException;
import com.hcemanager.exceptions.entities.*;
import com.hcemanager.models.entities.Organization;

import java.util.List;

/**
 * @author daniel, juan.
 */
public interface OrganizationDAO {

    public void insertOrganization(Organization organization) throws OrganizationExistsException, EntityExistsException;
    public void updateOrganization(Organization organization) throws OrganizationNotExistsException, OrganizationExistsException, EntityNotExistsException, EntityExistsException, CodeNotExistsException, CodeExistsException;
    public void deleteOrganization(String id) throws OrganizationNotExistsException, OrganizationExistsException, OrganizationCannotBeDeletedException, EntityCannotBeDeletedException, EntityNotExistsException, CodeNotExistsException;
    public Organization getOrganization(String id) throws OrganizationNotExistsException, CodeNotExistsException;
    public List<Organization> getOrganizations() throws OrganizationNotExistsException, CodeNotExistsException;

}
