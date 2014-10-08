package com.hcemanager.dao.entities.implementations;

import com.hcemanager.dao.entities.rowMappers.PlaceRowMapper;
import com.hcemanager.dao.entities.interfaces.PlaceDAO;
import com.hcemanager.dao.springServices.SpringServices;
import com.hcemanager.exceptions.codes.CodeExistsException;
import com.hcemanager.exceptions.codes.CodeNotExistsException;
import com.hcemanager.exceptions.entities.*;
import com.hcemanager.models.entities.Place;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.util.List;

/**
 * @author daniel, juan.
 */
public class PlaceDAOImpl extends JdbcDaoSupport implements PlaceDAO {

    //------------------------------------------------------------------------------------------------------------------
    //SQL sentence constants.
    //------------------------------------------------------------------------------------------------------------------

    public static final String INSERT = "insert into Place(idPlace,mobileInd,addr,directionsText,positionText,gpsText, Entity_idEntity) values (?,?,?,?,?,?,?)";
    public static final String UPDATE = "update Place set mobileInd=?, addr=?, directionsText=?, positionText=?, gpsText=? where idPlace=?";
    public static final String DELETE = "delete from Place where idPlace=?";
    public static final String SELECT_PLACE = "select * from Place inner join Entity where Entity_idEntity=idEntity and idPlace=?";
    public static final String SELECT_PLACES = "select * from Place inner join Entity where Entity_idEntity=idEntity";



    //------------------------------------------------------------------------------------------------------------------
    //CRUD methods.
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Insert an Place.
     * @param place
     * @throws PlaceExistException
     */
    @Override
    public void insertPlace(Place place) throws PlaceExistException, EntityExistsException {
        try {

            SpringServices.getEntityDAO().insertEntity(place);

            getJdbcTemplate().update(INSERT,
                    place.getIdPlace(),
                    place.isMobileInd(),
                    place.getAddr(),
                    place.getDirectionsText(),
                    place.getPositionText(),
                    place.getGpsText(),
                    place.getId());

        }catch (DataIntegrityViolationException e){
            throw new PlaceExistException(e);
        }
    }

    /**
     * Update an Place.
     * @param place
     * @throws PlaceNotExistsException
     * @throws PlaceExistException
     */
    @Override
    public void updatePlace(Place place) throws PlaceNotExistsException, PlaceExistException, EntityNotExistsException, EntityExistsException, CodeExistsException, CodeNotExistsException {

        if (isDifferent(place)){

            try {

                SpringServices.getEntityDAO().updateEntity(place);

                int rows = getJdbcTemplate().update(UPDATE,
                        place.isMobileInd(),
                        place.getAddr(),
                        place.getDirectionsText(),
                        place.getPositionText(),
                        place.getGpsText(),
                        place.getIdPlace());
                if (rows==0){
                    throw new PlaceNotExistsException();
                }
            }catch (DataIntegrityViolationException e){
                throw new PlaceExistException(e);
            }
        }else {
            SpringServices.getEntityDAO().updateEntity(place);
        }

    }

    /**
     * Delete an Place.
     * @param id
     * @throws PlaceNotExistsException
     * @throws PlaceCannnoyBeDeletedException
     */
    @Override
    public void deletePlace(String id) throws PlaceNotExistsException, PlaceCannnoyBeDeletedException, EntityCannotBeDeletedException, EntityNotExistsException, CodeNotExistsException {
        try {

            Place place = getPlace(id);

            int rows = getJdbcTemplate().update(DELETE, id);
            if (rows==0){
                throw new PlaceNotExistsException();
            }

            SpringServices.getEntityDAO().deleteEntity(place.getId());

        }catch (DataIntegrityViolationException e){
            throw new PlaceCannnoyBeDeletedException(e);
        }
    }

    /**
     * Get a Place.
     * @param id
     * @return Place.
     * @throws PlaceNotExistsException
     */
    @Override
    public Place getPlace(String id) throws PlaceNotExistsException, CodeNotExistsException {
        try {
            Place place = getJdbcTemplate().queryForObject(SELECT_PLACE, new PlaceRowMapper(), id);
            SpringServices.getEntityDAO().setEntityCodes(place,
                    SpringServices.getEntityDAO().getEntityCodes(place.getId()));
            return place;
        }catch (EmptyResultDataAccessException e){
            throw new PlaceNotExistsException(e);
        }
    }

    /**
     * Get all Places.
     * @return List<Place>
     * @throws PlaceNotExistsException
     */
    @Override
    public List<Place> getPlaces() throws PlaceNotExistsException, CodeNotExistsException {
        try {
            List<Place> places = getJdbcTemplate().query(SELECT_PLACES, new PlaceRowMapper());
            for (Place place:places){
                SpringServices.getEntityDAO().setEntityCodes(place,
                        SpringServices.getEntityDAO().getEntityCodes(place.getId()));
            }
            return places;
        }catch (EmptyResultDataAccessException e){
            throw new PlaceNotExistsException(e);
        }
    }

    private boolean isDifferent(Place place) throws PlaceNotExistsException, CodeNotExistsException {
        Place oldPlace = getPlace(place.getIdPlace());

        int attrDifferent=0;

        if (!place.getAddr().equals(oldPlace.getAddr())){
            attrDifferent++;
        }
        if (!place.getDirectionsText().equals(oldPlace.getDirectionsText())){
            attrDifferent++;
        }
        if (!place.getPositionText().equals(oldPlace.getPositionText())){
            attrDifferent++;
        }
        if (!place.getGpsText().equals(oldPlace.getGpsText())){
            attrDifferent++;
        }
         return attrDifferent!=0;
    }
}
