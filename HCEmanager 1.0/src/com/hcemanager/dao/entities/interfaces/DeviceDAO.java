package com.hcemanager.dao.entities.interfaces;

import com.hcemanager.exceptions.codes.CodeExistsException;
import com.hcemanager.exceptions.codes.CodeNotExistsException;
import com.hcemanager.exceptions.entities.*;
import com.hcemanager.models.entities.Device;

import java.util.List;

/**
 * This interface define the Device methods.
 * @author daniel, juan.
 */
public interface DeviceDAO {

    //------------------------------------------------------------------------------------------------------------------
    //CRUD methods.
    //------------------------------------------------------------------------------------------------------------------

    public void insertDevice(Device device) throws DeviceExistsException, ManufacturedMaterialExistsException, MaterialExistsException, EntityExistsException;
    public void updateDevice(Device device) throws DeviceExistsException, DeviceNotExistsException, ManufacturedMaterialExistsException, ManufacturedMaterialNotExistsException, MaterialExistsException, MaterialNotExistsException, EntityNotExistsException, EntityExistsException, CodeExistsException, CodeNotExistsException;
    public void deleteDevice(String id) throws DeviceNotExistsException, DeviceCannotBeDeletedException, ManufacturedMaterialNotExistsException, ManufacturedMaterialCannotBeDeletedException, MaterialCannotBeDeletedException, MaterialNotExistsException, EntityCannotBeDeletedException, EntityNotExistsException, CodeNotExistsException;
    public Device getDevice(String id) throws DeviceNotExistsException, CodeNotExistsException;
    public List<Device> getDevices() throws DeviceNotExistsException, CodeNotExistsException;
}
