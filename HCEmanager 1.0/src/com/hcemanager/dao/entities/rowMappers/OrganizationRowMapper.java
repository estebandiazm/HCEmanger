package com.hcemanager.dao.entities.rowMappers;

import com.hcemanager.models.entities.Entity;
import com.hcemanager.models.entities.Organization;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author daniel, juan.
 */
public class OrganizationRowMapper implements ParameterizedRowMapper<Organization> {


    EntityRowMapper entityRowMapper = new EntityRowMapper();
        /**
     * This class make a Organization mapper
     * @param resultSet
     * @param i
     * @return
     * @throws java.sql.SQLException
     */
    @Override
    public Organization mapRow(ResultSet resultSet, int i) throws SQLException {

        Entity entity = entityRowMapper.mapRow(resultSet, i);
        Organization organization = new Organization();

        //--------------------------------------------------------------------------------------------------------------
        //Organization arguments
        //--------------------------------------------------------------------------------------------------------------
        organization.setIdOrganization(resultSet.getString("idOrganization"));
        organization.setAddr(resultSet.getString("addr"));

        //--------------------------------------------------------------------------------------------------------------
        //Entity Arguments
        //--------------------------------------------------------------------------------------------------------------

        organization.setId(entity.getId());
        organization.setQuantity(entity.getQuantity());
        organization.setName(entity.getName());
        organization.setDescription(entity.getDescription());
        organization.setExistenceTime(entity.getExistenceTime());
        organization.setTelecom(entity.getTelecom());

        //TODO:pruebas.

        return organization;
    }
}
