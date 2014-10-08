package com.hcemanager.dao.acts.interfaces;

import com.hcemanager.exceptions.acts.*;
import com.hcemanager.exceptions.codes.CodeExistsException;
import com.hcemanager.exceptions.codes.CodeIsNotValidException;
import com.hcemanager.exceptions.codes.CodeNotExistsException;
import com.hcemanager.models.acts.Diet;

import java.util.List;

/**
 * This interface define the Diet DAO methods
 * @author daniel, estaban;
 */
public interface DietDAO {

    public void insertDiet(Diet diet) throws DietExistsException, SupplyExistsException, ActExistsException, CodeExistsException;
    public void updateDiet(Diet diet) throws DietExistsException, DietNotExistsException, SupplyExistsException, SupplyNotExistsException, ActNotExistsException, ActExistsException, CodeIsNotValidException, CodeNotExistsException, CodeExistsException;
    public void deleteDiet(String id) throws DietNotExistsException, DietCannotBeDeleted, SupplyNotExistsException, SupplyCannotBeDeletedException, ActNotExistsException, ActCannotBeDeleted, CodeIsNotValidException, CodeNotExistsException;
    public Diet getDiet(String id) throws DietNotExistsException, CodeNotExistsException, CodeIsNotValidException;
    public List<Diet> getDiets() throws DietNotExistsException, CodeNotExistsException, CodeIsNotValidException;
}
