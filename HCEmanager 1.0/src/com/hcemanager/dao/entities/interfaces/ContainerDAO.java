package com.hcemanager.dao.entities.interfaces;

import com.hcemanager.exceptions.codes.CodeExistsException;
import com.hcemanager.exceptions.codes.CodeNotExistsException;
import com.hcemanager.exceptions.entities.*;
import com.hcemanager.models.entities.Container;

import java.util.List;

/**
 * This interface define Container methods
 * @author daniel, juan.
 */
public interface ContainerDAO {

    //------------------------------------------------------------------------------------------------------------------
    //CRUD methods.
    //------------------------------------------------------------------------------------------------------------------

    public void insertContainer(Container container) throws ContainerExistsException, ManufacturedMaterialExistsException, MaterialExistsException, EntityExistsException;
    public void updateContainer(Container container) throws ContainerExistsException, ContainerNotExistsException, ManufacturedMaterialExistsException, ManufacturedMaterialNotExistsException, MaterialExistsException, MaterialNotExistsException, EntityNotExistsException, EntityExistsException, CodeExistsException, CodeNotExistsException;
    public void deleteContainer(String id) throws ContainerNotExistsException, ContainerCannotBeDeletedException, ManufacturedMaterialNotExistsException, ManufacturedMaterialCannotBeDeletedException, MaterialCannotBeDeletedException, MaterialNotExistsException, EntityCannotBeDeletedException, EntityNotExistsException, CodeNotExistsException;
    public Container getContainer(String id) throws ContainerNotExistsException, CodeNotExistsException;
    public List<Container> getContainers() throws ContainerNotExistsException, CodeNotExistsException;

}
