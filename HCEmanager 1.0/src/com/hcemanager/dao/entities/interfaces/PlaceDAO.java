package com.hcemanager.dao.entities.interfaces;

import com.hcemanager.exceptions.codes.CodeExistsException;
import com.hcemanager.exceptions.codes.CodeNotExistsException;
import com.hcemanager.exceptions.entities.*;
import com.hcemanager.models.entities.Place;

import java.util.List;

/**
 * @author daniel, juan.
 */
public interface PlaceDAO {

    public void insertPlace(Place place) throws PlaceExistException, EntityExistsException;
    public void updatePlace(Place place) throws PlaceNotExistsException, PlaceExistException, EntityNotExistsException, EntityExistsException, CodeNotExistsException, CodeExistsException;
    public void deletePlace(String id) throws PlaceNotExistsException, PlaceCannnoyBeDeletedException, EntityCannotBeDeletedException, EntityNotExistsException, CodeNotExistsException;
    public Place getPlace(String id) throws PlaceNotExistsException, CodeNotExistsException;
    public List<Place> getPlaces() throws PlaceNotExistsException, CodeNotExistsException;


}
