package com.hcemanager.dao.roles.rowMappers;

import com.hcemanager.models.roles.Access;
import com.hcemanager.models.roles.Role;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author daniel, juan.
 */
public class AccessRowMapper implements ParameterizedRowMapper<Access> {

    RoleRowMapper roleRowMapper = new RoleRowMapper();

    /**
     * This methods make a Access mapper.
     * @param resultSet
     * @param i
     * @return
     * @throws java.sql.SQLException
     */
    @Override
    public Access mapRow(ResultSet resultSet, int i) throws SQLException {

        Role role = roleRowMapper.mapRow(resultSet,i);
        Access access = new Access();

        //--------------------------------------------------------------------------------------------------------------
        //Access arguments
        //--------------------------------------------------------------------------------------------------------------
        access.setIdAccess(resultSet.getString("idAccess"));
        access.setGaugeQuantity(resultSet.getString("gaugeQuantity"));

        //--------------------------------------------------------------------------------------------------------------
        //Role arguments
        //--------------------------------------------------------------------------------------------------------------
        access.setId(role.getId());
        access.setNegationInd(role.isNegationInd());
        access.setName(role.getName());
        access.setAddr(role.getAddr());
        access.setTelecom(role.getTelecom());
        access.setEffectiveTime(role.getEffectiveTime());
        access.setCertificateText(role.getCertificateText());
        access.setQuantity(role.getQuantity());
        access.setPositionNumber(role.getPositionNumber());

        //TODO: pruebas.

        return access;
    }
}
