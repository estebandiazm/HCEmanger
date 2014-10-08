package com.hcemanager.dao.roles.interfaces;

import com.hcemanager.exceptions.codes.CodeExistsException;
import com.hcemanager.exceptions.codes.CodeNotExistsException;
import com.hcemanager.exceptions.roles.*;
import com.hcemanager.models.roles.Employee;

import java.util.List;

/**
 * @author Daniel Bellon & Juan Diaz
 */
public interface EmployeeDAO {

    public void insertEmployee(Employee employee) throws EmployeeExistsException, RoleExistsException;
    public void updateEmployee(Employee employee) throws EmployeeNotExistsException, EmployeeExistsException,
            RoleExistsException, RoleNotExistsException, CodeNotExistsException, CodeExistsException;
    public void deleteEmployee(String id) throws EmployeeNotExistsException, EmployeeCannotBeDeletedException,
            RoleNotExistsException, RoleCannotBeDeletedException, CodeNotExistsException;
    public Employee getEmployee(String id) throws EmployeeNotExistsException, CodeNotExistsException;
    public List<Employee> getEmployees() throws EmployeeNotExistsException, CodeNotExistsException;
}
