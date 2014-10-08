package com.hcemanager.dao.entities.rowMappers;

import com.hcemanager.models.entities.Entity;
import com.hcemanager.models.entities.Place;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author daniel, juan.
 */
public class PlaceRowMapper implements ParameterizedRowMapper<Place> {

    EntityRowMapper entityRowMapper = new EntityRowMapper();

    /**
     * This class make a Place mapper.
     * @param resultSet
     * @param i
     * @return
     * @throws SQLException
     */
    @Override
    public Place mapRow(ResultSet resultSet, int i) throws SQLException {

        Entity entity = entityRowMapper.mapRow(resultSet,i);
        Place place = new Place();

        //--------------------------------------------------------------------------------------------------------------
        //Place arguments
        //--------------------------------------------------------------------------------------------------------------
        place.setIdPlace(resultSet.getString("idPlace"));
        place.setMobileInd(resultSet.getBoolean("mobileInd"));
        place.setAddr(resultSet.getString("addr"));
        place.setDirectionsText(resultSet.getString("directionsText"));
        place.setPositionText(resultSet.getString("positionText"));
        place.setGpsText(resultSet.getString("gpsText"));

        //--------------------------------------------------------------------------------------------------------------
        //entity arguments
        //--------------------------------------------------------------------------------------------------------------
        place.setId(entity.getId());
        place.setQuantity(entity.getQuantity());
        place.setName(entity.getName());
        place.setDescription(entity.getDescription());
        place.setExistenceTime(entity.getExistenceTime());
        place.setTelecom(entity.getTelecom());


        //TODO: pruebas.

        return place;
    }
}
