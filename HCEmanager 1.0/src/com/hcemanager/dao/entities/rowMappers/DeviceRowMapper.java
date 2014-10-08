package com.hcemanager.dao.entities.rowMappers;

import com.hcemanager.models.entities.Device;
import com.hcemanager.models.entities.ManufacturedMaterial;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author daniel, juan.
 */
public class DeviceRowMapper implements ParameterizedRowMapper<Device> {

    ManufactureMaterialRowMapper manufactureMaterialRowMapper = new ManufactureMaterialRowMapper();

    /**
     * This method make an Device mapper.
     * @param resultSet
     * @param i
     * @return
     * @throws java.sql.SQLException
     */
    public Device mapRow(ResultSet resultSet,int i) throws SQLException{

        ManufacturedMaterial manufacturedMaterial = manufactureMaterialRowMapper.mapRow(resultSet,i);
        Device device = new Device();

        //--------------------------------------------------------------------------------------------------------------
        //Device arguments
        //--------------------------------------------------------------------------------------------------------------
        device.setIdDevice(resultSet.getString("idDevice"));
        device.setManufacturerModelName(resultSet.getString("manufacturerModelName"));
        device.setSoftwareName(resultSet.getString("softwareName"));
        device.setLastCalibrationTime(resultSet.getString("lastCalibrationTime"));

        //--------------------------------------------------------------------------------------------------------------
        //ManufacturedMaterial arguments
        //--------------------------------------------------------------------------------------------------------------
        device.setIdManufacturedMaterial(manufacturedMaterial.getIdManufacturedMaterial());
        device.setLotNumberText(manufacturedMaterial.getLotNumberText());
        device.setExpirationTime(manufacturedMaterial.getExpirationTime());
        device.setStabilityTime(manufacturedMaterial.getStabilityTime());

        //--------------------------------------------------------------------------------------------------------------
        //Material arguments
        //--------------------------------------------------------------------------------------------------------------
        device.setIdMaterial(manufacturedMaterial.getIdMaterial());

        //--------------------------------------------------------------------------------------------------------------
        //Entity arguments
        //--------------------------------------------------------------------------------------------------------------
        device.setId(manufacturedMaterial.getId());
        device.setQuantity(manufacturedMaterial.getQuantity());
        device.setName(manufacturedMaterial.getName());
        device.setDescription(manufacturedMaterial.getDescription());
        device.setExistenceTime(manufacturedMaterial.getExistenceTime());
        device.setTelecom(manufacturedMaterial.getTelecom());



        return device;
    }
}
