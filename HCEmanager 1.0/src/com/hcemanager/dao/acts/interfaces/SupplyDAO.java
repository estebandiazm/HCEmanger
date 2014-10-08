package com.hcemanager.dao.acts.interfaces;

import com.hcemanager.exceptions.acts.*;
import com.hcemanager.exceptions.codes.CodeExistsException;
import com.hcemanager.exceptions.codes.CodeIsNotValidException;
import com.hcemanager.exceptions.codes.CodeNotExistsException;
import com.hcemanager.models.acts.Supply;

import java.util.List;

/**
 * This interface define the Supply DAO methods
 * @author daniel, esteban.
 */
public interface SupplyDAO {

    public void insertSupply(Supply supply) throws SupplyExistsException, ActExistsException, CodeExistsException;
    public void updateSupply(Supply supply) throws SupplyExistsException, SupplyNotExistsException, ActNotExistsException, ActExistsException, CodeIsNotValidException, CodeNotExistsException, CodeExistsException;
    public void deleteSupply(String id) throws SupplyNotExistsException, SupplyCannotBeDeletedException, ActNotExistsException, ActCannotBeDeleted;
    public Supply getSupply(String id) throws SupplyNotExistsException;
    public List<Supply> getSupplies() throws SupplyNotExistsException;
}
