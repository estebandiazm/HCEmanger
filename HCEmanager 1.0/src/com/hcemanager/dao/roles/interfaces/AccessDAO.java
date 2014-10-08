package com.hcemanager.dao.roles.interfaces;

import com.hcemanager.exceptions.codes.CodeExistsException;
import com.hcemanager.exceptions.codes.CodeNotExistsException;
import com.hcemanager.exceptions.roles.*;
import com.hcemanager.models.codes.Code;
import com.hcemanager.models.roles.Access;

import java.util.List;

/**
 * @author daniel, esteban.
 */
public interface AccessDAO {

    public void insertAccess(Access access) throws AccessExistsException, RoleExistsException;
    public void updateAccess(Access access) throws AccessNotExistsException, AccessExistsException, RoleExistsException, RoleNotExistsException, CodeNotExistsException, CodeExistsException;
    public void deleteAccess(String id) throws AccessNotExistsException, AccessCannotBeDeletedException, RoleNotExistsException, CodeNotExistsException, RoleCannotBeDeletedException;
    public Access getAccess(String id) throws AccessNotExistsException, CodeNotExistsException;
    public List<Access> getAccesses() throws AccessNotExistsException, CodeNotExistsException;
    public void setAccessCodes(Access access, List<Code> codes) throws CodeNotExistsException;
    public List<Code> getAccessCodes(String id) throws CodeNotExistsException;
}
