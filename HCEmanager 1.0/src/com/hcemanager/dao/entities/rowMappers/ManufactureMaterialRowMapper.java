package com.hcemanager.dao.entities.rowMappers;

import com.hcemanager.models.entities.ManufacturedMaterial;
import com.hcemanager.models.entities.Material;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author daniel, juan.
 */
public class ManufactureMaterialRowMapper implements ParameterizedRowMapper<ManufacturedMaterial> {

    MaterialRowMapper materialRowMapper = new MaterialRowMapper();

    /**
     * This methods make a ManufactureMaterial mapper.
     * @param resultSet
     * @param i
     * @return
     * @throws java.sql.SQLException
     */
    @Override
    public ManufacturedMaterial mapRow(ResultSet resultSet, int i) throws SQLException {

        Material material = materialRowMapper.mapRow(resultSet,i);
        ManufacturedMaterial manufacturedMaterial = new ManufacturedMaterial();

        //--------------------------------------------------------------------------------------------------------------
        //ManufacturedMaterial arguments
        //--------------------------------------------------------------------------------------------------------------
        manufacturedMaterial.setIdManufacturedMaterial(resultSet.getString("idManufacturedMaterial"));
        manufacturedMaterial.setLotNumberText(resultSet.getString("lotNumberText"));
        manufacturedMaterial.setExpirationTime(resultSet.getString("expirationTime"));
        manufacturedMaterial.setStabilityTime(resultSet.getString("stabilityTime"));

        //--------------------------------------------------------------------------------------------------------------
        //Material arguments
        //--------------------------------------------------------------------------------------------------------------
        manufacturedMaterial.setIdMaterial(material.getIdMaterial());

        //--------------------------------------------------------------------------------------------------------------
        //Entity arguments
        //--------------------------------------------------------------------------------------------------------------
        manufacturedMaterial.setId(material.getId());
        manufacturedMaterial.setQuantity(material.getQuantity());
        manufacturedMaterial.setName(material.getName());
        manufacturedMaterial.setDescription(material.getDescription());
        manufacturedMaterial.setExistenceTime(material.getExistenceTime());
        manufacturedMaterial.setTelecom(material.getTelecom());


        //TODO: pruebas.

        return manufacturedMaterial;
    }
}
