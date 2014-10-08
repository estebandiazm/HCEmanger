package com.hcemanager.dao.roles.rowMappers;

import com.hcemanager.models.roles.Patient;
import com.hcemanager.models.roles.Role;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author daniel, juan.
 */
public class PatientRowMapper implements ParameterizedRowMapper<Patient> {

    RoleRowMapper roleRowMapper = new RoleRowMapper();

    /**
     * This method make a Patient mapper.
     * @param resultSet
     * @param i
     * @return Patient
     * @throws java.sql.SQLException
     */
    @Override
    public Patient mapRow(ResultSet resultSet, int i) throws SQLException {

        Role role = roleRowMapper.mapRow(resultSet,i);
        Patient patient = new Patient();

        //--------------------------------------------------------------------------------------------------------------
        //Patient arguments
        //--------------------------------------------------------------------------------------------------------------
        patient.setIdPatient(resultSet.getString("idPatient"));

        //--------------------------------------------------------------------------------------------------------------
        //Role arguments
        //--------------------------------------------------------------------------------------------------------------
        patient.setId(role.getId());
        patient.setNegationInd(role.isNegationInd());
        patient.setName(role.getName());
        patient.setAddr(role.getAddr());
        patient.setTelecom(role.getTelecom());
        patient.setEffectiveTime(role.getEffectiveTime());
        patient.setCertificateText(role.getCertificateText());
        patient.setQuantity(role.getQuantity());
        patient.setPositionNumber(role.getPositionNumber());



        return patient;
    }
}
