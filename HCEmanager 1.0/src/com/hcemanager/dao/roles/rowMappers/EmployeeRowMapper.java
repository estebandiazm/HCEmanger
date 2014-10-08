package com.hcemanager.dao.roles.rowMappers;

import com.hcemanager.models.roles.Employee;
import com.hcemanager.models.roles.Role;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Daniel Bellon & Juan Diaz
 */
public class EmployeeRowMapper implements ParameterizedRowMapper<Employee> {

    RoleRowMapper roleRowMapper = new RoleRowMapper();

    /**
     * This methods make a Employee mapper.
     * @param resultSet
     * @param i
     * @return
     * @throws java.sql.SQLException
     */
    @Override
    public Employee mapRow(ResultSet resultSet, int i) throws SQLException {

        Role role = roleRowMapper.mapRow(resultSet,i);
        Employee employee = new Employee();

        //--------------------------------------------------------------------------------------------------------------
        //Employee arguments
        //--------------------------------------------------------------------------------------------------------------
        employee.setIdEmployee(resultSet.getString("idEmployee"));
        employee.setJobTitleName(resultSet.getString("jobTitleName"));
        employee.setSalaryQuantity(resultSet.getString("salaryQuantity"));
        employee.setHazardExposureText(resultSet.getString("hazardExposureText"));
        employee.setProtectiveEquipmentText(resultSet.getString("protectiveEquipmentText"));
        //--------------------------------------------------------------------------------------------------------------
        //Role arguments
        //--------------------------------------------------------------------------------------------------------------
        employee.setId(role.getId());
        employee.setNegationInd(role.isNegationInd());
        employee.setName(role.getName());
        employee.setAddr(role.getAddr());
        employee.setTelecom(role.getTelecom());
        employee.setEffectiveTime(role.getEffectiveTime());
        employee.setCertificateText(role.getCertificateText());
        employee.setQuantity(role.getQuantity());
        employee.setPositionNumber(role.getPositionNumber());

        return employee;
    }
}
