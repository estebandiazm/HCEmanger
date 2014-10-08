package com.hcemanager.dao.entities.rowMappers;

import com.hcemanager.models.entities.Container;
import com.hcemanager.models.entities.ManufacturedMaterial;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import java.lang.reflect.ParameterizedType;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class Mapping Container.
 * @author daniel, juan.
 */
public class ContainerRowMapper implements ParameterizedRowMapper<Container> {

    ManufactureMaterialRowMapper manufactureMaterialRowMapper = new ManufactureMaterialRowMapper();

    /**
     * This method make an the Container mapper.
     * @param resultSet
     * @param i
     * @return
     * @throws java.sql.SQLException
     */

    public Container mapRow(ResultSet resultSet, int i) throws SQLException{

        ManufacturedMaterial manufacturedMaterial = manufactureMaterialRowMapper.mapRow(resultSet,i);
        Container container = new Container();

        //--------------------------------------------------------------------------------------------------------------
        //Container arguments
        //--------------------------------------------------------------------------------------------------------------
        container.setIdContainer(resultSet.getString("idContainer"));//Nombre especifico de la base da datos
        container.setCapacityQuantity(resultSet.getString("capacityQuantity"));
        container.setHeightQuantity(resultSet.getString("heightQuantity"));
        container.setDiameterQuantity(resultSet.getString("diameterQuantity"));
        container.setBarrierDeltaQuantity(resultSet.getString("barrierDeltaQuantity"));
        container.setBottomDeltaQuantity(resultSet.getString("bottomDeltaQuantity"));

        //--------------------------------------------------------------------------------------------------------------
        //ManufacturedMaterial arguments
        //--------------------------------------------------------------------------------------------------------------
        container.setIdManufacturedMaterial(manufacturedMaterial.getIdManufacturedMaterial());
        container.setLotNumberText(manufacturedMaterial.getLotNumberText());
        container.setExpirationTime(manufacturedMaterial.getExpirationTime());
        container.setStabilityTime(manufacturedMaterial.getStabilityTime());

        //--------------------------------------------------------------------------------------------------------------
        //Material arguments
        //--------------------------------------------------------------------------------------------------------------
        container.setIdMaterial(manufacturedMaterial.getIdMaterial());

        //--------------------------------------------------------------------------------------------------------------
        //Entity arguments
        //--------------------------------------------------------------------------------------------------------------
        container.setId(manufacturedMaterial.getId());
        container.setQuantity(manufacturedMaterial.getQuantity());
        container.setName(manufacturedMaterial.getName());
        container.setDescription(manufacturedMaterial.getDescription());
        container.setExistenceTime(manufacturedMaterial.getExistenceTime());
        container.setTelecom(manufacturedMaterial.getTelecom());

        //TODO: pruebas.


        return container;
    }
}
