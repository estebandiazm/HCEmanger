package com.hcemanager.dao.roles.rowMappers;

import com.hcemanager.models.roles.Role;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author daniel, juan.
 */
public class RoleRowMapper implements ParameterizedRowMapper<Role> {

    /**
     * This method make a Role mapper.
     * @param resultSet
     * @param i
     * @return
     * @throws SQLException
     */
    @Override
    public Role mapRow(ResultSet resultSet, int i) throws SQLException {

        Role role = new Role();
        role.setId(resultSet.getString("idRole"));
        role.setNegationInd(resultSet.getBoolean("negationInd"));
        role.setName(resultSet.getString("name"));
        role.setAddr(resultSet.getString("addr"));
        role.setTelecom(resultSet.getString("telecom"));
        role.setEffectiveTime(resultSet.getString("effectiveTime"));
        role.setCertificateText(resultSet.getString("certificateText"));
        role.setQuantity(resultSet.getString("quantity"));
        role.setPositionNumber(resultSet.getString("positionNumber"));

        //TODO: completar codes y foreign key.
        return role;
    }
}
