package com.hcemanager.dao.connectors.rowMappers;

import com.hcemanager.models.connectors.RoleHasEntity;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Daniel Bellon & Juan Diaz
 */
public class RoleHasEntityRowMapper implements ParameterizedRowMapper<RoleHasEntity> {

    @Override
    public RoleHasEntity mapRow(ResultSet resultSet, int i) throws SQLException {

        RoleHasEntity roleHasEntity = new RoleHasEntity();

        roleHasEntity.setIdRole(resultSet.getString("Role_idRole"));
        roleHasEntity.setIdEntity(resultSet.getString("Entity_idEntity"));

        return roleHasEntity;
    }
}
