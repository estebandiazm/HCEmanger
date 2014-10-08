package com.hcemanager.dao.roles.interfaces;

import com.hcemanager.exceptions.codes.CodeNotExistsException;
import com.hcemanager.exceptions.roles.RoleCannotBeDeletedException;
import com.hcemanager.exceptions.roles.RoleExistsException;
import com.hcemanager.exceptions.roles.RoleNotExistsException;
import com.hcemanager.models.codes.Code;
import com.hcemanager.models.roles.Role;

import java.util.List;

/**
 * @author daniel.
 */
public interface RoleDAO {

    public void insertRole(Role role) throws RoleExistsException;
    public void updateRole(Role role) throws RoleNotExistsException, RoleExistsException, CodeNotExistsException;
    public void deleteRole(String id) throws RoleNotExistsException, RoleCannotBeDeletedException, RoleCannotBeDeletedException;
    public Role getRole(String id) throws RoleNotExistsException, CodeNotExistsException;
    public List<Role> getRoles() throws RoleNotExistsException, CodeNotExistsException;
    public List<Code> getRoleCodes(String id) throws CodeNotExistsException;
    public void setRoleCodes(Role role, List<Code> codes);
}
