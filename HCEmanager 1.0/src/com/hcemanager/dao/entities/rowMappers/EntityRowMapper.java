package com.hcemanager.dao.entities.rowMappers;

import com.hcemanager.models.entities.Device;
import com.hcemanager.models.entities.Entity;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author daniel, juan.
 */
public class EntityRowMapper implements ParameterizedRowMapper<Entity> {

    /**
     * This method make a Entity mapper.
     * @param resultSet
     * @param i
     * @return
     * @throws SQLException
     */
    @Override
    public Entity mapRow(ResultSet resultSet, int i) throws SQLException {
        Entity entity = new Entity();
        entity.setId(resultSet.getString("idEntity"));
        entity.setQuantity(resultSet.getString("quantity"));
        entity.setName(resultSet.getString("name"));
        entity.setDescription(resultSet.getString("description"));
        entity.setExistenceTime(resultSet.getString("existenceTime"));
        entity.setTelecom(resultSet.getString("telecom"));



        return entity;
    }
}
