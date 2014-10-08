package com.hcemanager.dao.roles.implementations;

import com.hcemanager.dao.codes.CodeRowMapper;
import com.hcemanager.dao.roles.interfaces.EmployeeDAO;
import com.hcemanager.dao.roles.rowMappers.EmployeeRowMapper;
import com.hcemanager.dao.springServices.SpringServices;
import com.hcemanager.exceptions.codes.CodeExistsException;
import com.hcemanager.exceptions.codes.CodeNotExistsException;
import com.hcemanager.exceptions.roles.*;
import com.hcemanager.models.codes.Code;
import com.hcemanager.models.roles.Employee;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.util.List;

/**
 * @author Daniel Bellon & Juan Diaz
 */
public class EmployeeDAOImpl extends JdbcDaoSupport implements EmployeeDAO {

    //------------------------------------------------------------------------------------------------------------------
    //SQL sentences
    //------------------------------------------------------------------------------------------------------------------
    public static final String INSERT = "insert into Employee(idEmployee,jobTitleName,salaryQuantity,hazardExposureText," +
            "protectiveEquipmentText,Role_idRole) values (?,?,?,?,?,?)";
    public static final String UPDATE = "update Employee set jobTitleName=?, salaryQuantity=?, hazardExposureText=?, " +
            "protectiveEquipmentText=? where idEmployee=?";
    public static final String DELETE = "delete from Employee where idEmployee=?";
    public static final String SELECT_EMPLOYEE = "select * from Employee inner join Role where Role_idRole=idRole and idEmployee=?";
    public static final String SELECT_EMPLOYEES = "select * from Employee inner join Role where Role_idRole=idRole";
    public static final String INSERT_CODE = "insert into Employee_has_Codes (Codes_idCodes,Employee_idEmployee,type) values (?,?,?);";
    public static final String UPDATE_CODE = "update Employee_has_Codes set Codes_idCodes = ? where Employee_idEmployee=? and type=?;";
    public static final String DELETE_CODE = "delete from Employee_has_Codes where Employee_idEmployee=?";
    public static final String SELECT_CODES = "select * from Employee_has_Codes inner join Codes where Codes_idCodes=idCodes " +
            "and Employee_idEmployee=?";
    //------------------------------------------------------------------------------------------------------------------
    //CRUD methods
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Insert an Employee
     * @param employee an Employee object
     * @throws EmployeeExistsException
     */
    @Override
    public void insertEmployee(Employee employee) throws EmployeeExistsException, RoleExistsException {
        try{

            SpringServices.getRoleDAO().insertRole(employee);

            getJdbcTemplate().update(INSERT,
                    employee.getIdEmployee(),
                    employee.getJobTitleName(),
                    employee.getSalaryQuantity(),
                    employee.getHazardExposureText(),
                    employee.getProtectiveEquipmentText(),
                    employee.getId());

            //Codes

            getJdbcTemplate().update(INSERT_CODE,
                    employee.getJobCode().getId(),
                    employee.getIdEmployee(),
                    employee.getJobCode().getType());

            getJdbcTemplate().update(INSERT_CODE,
                    employee.getJobClassCode().getId(),
                    employee.getIdEmployee(),
                    employee.getJobClassCode().getType());

            getJdbcTemplate().update(INSERT_CODE,
                    employee.getOccupationCode().getId(),
                    employee.getIdEmployee(),
                    employee.getOccupationCode().getType());

            getJdbcTemplate().update(INSERT_CODE,
                    employee.getSalaryTypeCode().getId(),
                    employee.getIdEmployee(),
                    employee.getSalaryTypeCode().getType());


        }catch (DataIntegrityViolationException e){
            throw new EmployeeExistsException(e);
        }

    }

    /**
     * Update an Employee.
     * @param employee an Employee object
     * @throws com.hcemanager.exceptions.roles.EmployeeNotExistsException
     * @throws com.hcemanager.exceptions.roles.EmployeeExistsException
     */
    @Override
    public void updateEmployee(Employee employee) throws EmployeeNotExistsException, EmployeeExistsException, RoleExistsException, RoleNotExistsException, CodeNotExistsException, CodeExistsException {

        if (isDifferent(employee)){

            try{

                SpringServices.getRoleDAO().updateRole(employee);

                int rows = getJdbcTemplate().update(UPDATE,
                        employee.getJobTitleName(),
                        employee.getSalaryQuantity(),
                        employee.getHazardExposureText(),
                        employee.getProtectiveEquipmentText(),
                        employee.getIdEmployee());

                if (rows==0){
                    throw new EmployeeNotExistsException();
                }
                updateCodes(employee);
            }catch (DataIntegrityViolationException e){
                throw new EmployeeExistsException(e);
            }
        }else {
            SpringServices.getRoleDAO().updateRole(employee);
            updateCodes(employee);
        }
    }

    /**
     * Delete an Employee.
     * @param id the Employee id
     * @throws EmployeeNotExistsException
     * @throws EmployeeCannotBeDeletedException
     */
    @Override
    public void deleteEmployee(String id) throws EmployeeNotExistsException, EmployeeCannotBeDeletedException, RoleNotExistsException, RoleCannotBeDeletedException, CodeNotExistsException {
        try {

            getJdbcTemplate().update(DELETE_CODE,id);

            int rows = getJdbcTemplate().update(DELETE, id);
            if (rows==0){
                throw new EmployeeNotExistsException();
            }

            Employee employee = getEmployee(id);
            SpringServices.getRoleDAO().deleteRole(employee.getId());

        }catch (DataIntegrityViolationException e){
            throw new EmployeeCannotBeDeletedException();
        }
    }

    /**
     * Get a Employee from the db
     * @param id the Employee id
     * @return employee
     * @throws EmployeeNotExistsException
     */
    @Override
    public Employee getEmployee(String id) throws EmployeeNotExistsException, CodeNotExistsException {
        try {
            Employee employee = getJdbcTemplate().queryForObject(SELECT_EMPLOYEE, new EmployeeRowMapper(), id);
            setEmployeeCodes(employee, getEmployeeCodes(id));
            return employee;
        }catch (EmptyResultDataAccessException e){
            throw new EmployeeNotExistsException(e);
        }
    }

    /**
     * Get all Employees.
     * @return List<Employee>
     * @throws EmployeeNotExistsException
     */
    @Override
    public List<Employee> getEmployees() throws EmployeeNotExistsException, CodeNotExistsException {
        try {
            List<Employee> employees = getJdbcTemplate().query(SELECT_EMPLOYEES, new EmployeeRowMapper());
            for (Employee employee:employees){
                setEmployeeCodes(employee, getEmployeeCodes(employee.getIdEmployee()));
                employees.set(employees.indexOf(employee),employee);
            }
            return employees;
        }catch (EmptyResultDataAccessException e){
            throw new EmployeeNotExistsException(e);
        }
    }

    private List<Code> getEmployeeCodes(String id) throws CodeNotExistsException {
        try {

            List<Code> codes = getJdbcTemplate().query(SELECT_CODES, new CodeRowMapper(), id);
            return codes;

        }catch (EmptyResultDataAccessException e){
            throw new CodeNotExistsException(e);
        }
    }


    private void setEmployeeCodes(Employee employee, List<Code> codes) throws CodeNotExistsException {

        SpringServices.getRoleDAO().setRoleCodes(employee, SpringServices.getRoleDAO().getRoleCodes(employee.getId()));

        for (Code code: codes){

            String typeCode = code.getType();

            if(typeCode.equals("jobCode")){
                employee.setJobCode(code);
            }else if(typeCode.equals("jobClassCode")){
                employee.setJobClassCode(code);
            }else if (typeCode.equals("occupationCode")){
                employee.setOccupationCode(code);
            }else if (typeCode.equals("salaryTypeCode")){
                employee.setSalaryTypeCode(code);
            }

        }
    }

    private boolean isDifferent(Employee employee) throws EmployeeNotExistsException, CodeNotExistsException {
        Employee oldEmployee = getEmployee(employee.getIdEmployee());

        int attrDifferent=0;

        if (!employee.getSalaryQuantity().equals(oldEmployee.getSalaryQuantity())){
            attrDifferent++;
        }
        if (!employee.getJobTitleName().equals(oldEmployee.getJobTitleName())){
            attrDifferent++;
        }
        if (!employee.getHazardExposureText().equals(oldEmployee.getHazardExposureText())){
            attrDifferent++;
        }
        if (!employee.getProtectiveEquipmentText().equals(oldEmployee.getProtectiveEquipmentText())){
            attrDifferent++;
        }

        return attrDifferent!=0;
    }

    private void updateCodes(Employee employee) throws CodeNotExistsException, CodeExistsException {
        List<Code> oldCodes =   getEmployeeCodes(employee.getIdEmployee());

        for (Code code:oldCodes){
            try {
                if (code.getType().equalsIgnoreCase(employee.getJobCode().getType())){
                    if (!code.getId().equals(employee.getJobCode())){
                        getJdbcTemplate().update(UPDATE_CODE,
                                employee.getJobCode().getId(),
                                employee.getIdEmployee(),
                                employee.getJobCode().getType());
                    }
                }else if (code.getType().equalsIgnoreCase(employee.getJobClassCode().getType())){
                    if (!code.getId().equals(employee.getJobClassCode().getId())){
                        getJdbcTemplate().update(UPDATE_CODE,
                                employee.getJobClassCode().getId(),
                                employee.getIdEmployee(),
                                employee.getJobClassCode().getType());
                    }
                }else if (code.getType().equalsIgnoreCase(employee.getOccupationCode().getType())){
                    if (!code.getId().equals(employee.getOccupationCode().getId())){
                        getJdbcTemplate().update(UPDATE_CODE,
                                employee.getOccupationCode().getId(),
                                employee.getIdEmployee(),
                                employee.getOccupationCode().getType());
                    }
                }else if (code.getType().equalsIgnoreCase(employee.getSalaryTypeCode().getType())){
                    if (!code.getId().equals(employee.getSalaryTypeCode().getId())){
                        getJdbcTemplate().update(UPDATE_CODE,
                                employee.getSalaryTypeCode().getId(),
                                employee.getIdEmployee(),
                                employee.getSalaryTypeCode().getType());
                    }
                }

            }catch (DataIntegrityViolationException e){
                throw new CodeExistsException(e);
            }
        }
    }
}
