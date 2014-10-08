package com.hcemanager.dao.entities.rowMappers;

import com.hcemanager.models.entities.Entity;
import com.hcemanager.models.entities.Material;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author daniel, juan.
 */
public class MaterialRowMapper implements ParameterizedRowMapper<Material> {

    EntityRowMapper entityRowMapper = new EntityRowMapper();

    /**
     * This class make a Material mapper.
     * @param resultSet
     * @param i
     * @return
     * @throws java.sql.SQLException
     */
    @Override
    public Material mapRow(ResultSet resultSet, int i) throws SQLException {

        Entity entity= entityRowMapper.mapRow(resultSet, i);
        Material material = new Material();

        //--------------------------------------------------------------------------------------------------------------
        //Material Arguments
        //--------------------------------------------------------------------------------------------------------------

        material.setIdMaterial(resultSet.getString("idMaterial"));

        //--------------------------------------------------------------------------------------------------------------
        //Entity arguments
        //--------------------------------------------------------------------------------------------------------------

        material.setId(entity.getId());
        material.setQuantity(entity.getQuantity());
        material.setName(entity.getName());
        material.setDescription(entity.getDescription());
        material.setExistenceTime(entity.getExistenceTime());
        material.setTelecom(entity.getTelecom());


        //TODO: pruebas.

        return material;
    }
}
