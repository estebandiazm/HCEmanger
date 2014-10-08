package com.hcemanager.dao.entities.interfaces;

import com.hcemanager.exceptions.codes.CodeExistsException;
import com.hcemanager.exceptions.codes.CodeNotExistsException;
import com.hcemanager.exceptions.entities.*;
import com.hcemanager.models.entities.ManufacturedMaterial;

import java.util.List;

/**
 * @author daniel, juan.
 */
public interface ManufacturedMaterialDAO {

    public void insertManufacturedMaterial(ManufacturedMaterial manufacturedMaterial) throws ManufacturedMaterialExistsException, MaterialExistsException, EntityExistsException;
    public void updateManufacturedMaterial(ManufacturedMaterial manufacturedMaterial) throws ManufacturedMaterialNotExistsException, ManufacturedMaterialExistsException, MaterialExistsException, MaterialNotExistsException, EntityNotExistsException, EntityExistsException, CodeNotExistsException, CodeExistsException;
    public void deleteManufacturedMaterial(String id) throws ManufacturedMaterialNotExistsException, ManufacturedMaterialCannotBeDeletedException, MaterialCannotBeDeletedException, MaterialNotExistsException, EntityCannotBeDeletedException, EntityNotExistsException, CodeNotExistsException;
    public ManufacturedMaterial getManufacturedMaterial(String id) throws ManufacturedMaterialNotExistsException, CodeNotExistsException;
    public List<ManufacturedMaterial> getManufacturedMaterials() throws ManufacturedMaterialNotExistsException, CodeNotExistsException;

}
