package com.hcemanager.dao.entities.implementations;

import com.hcemanager.dao.codes.CodeRowMapper;
import com.hcemanager.dao.entities.interfaces.ContainerDAO;
import com.hcemanager.dao.entities.rowMappers.ContainerRowMapper;
import com.hcemanager.dao.springServices.SpringServices;
import com.hcemanager.exceptions.codes.CodeExistsException;
import com.hcemanager.exceptions.codes.CodeNotExistsException;
import com.hcemanager.exceptions.entities.*;
import com.hcemanager.models.codes.Code;
import com.hcemanager.models.entities.Container;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.util.List;

/**
 * The class implements the ContainerDAO interface and its methods.
 * @author daniel, juan.
 */
public class ContainerDAOImpl extends JdbcDaoSupport implements ContainerDAO {

    //------------------------------------------------------------------------------------------------------------------
    //SQL sentence constants
   //-------------------------------------------------------------------------------------------------------------------

    public static final String INSERT = "insert into Container (idContainer, capacityQuantity, heightQuantity, diameterQuantity, barrierDeltaQuantity, bottomDeltaQuantity, ManufacturedMaterial_idManufacturedMaterial) values (?,?,?,?,?,?,?)";
    public static final String UPDATE = "update Container set capacityQuantity=?, heightQuantity=?, diameterQuantity=?, barrierDeltaQuantity=?, bottomDeltaQuantity=? where idContainer=?";
    public static final String DELETE = "delete from Container where idContainer=?";
    public static final String SELECT_CONTAINER = "select * from Container inner join ManufacturedMaterial inner join Material inner join Entity where ManufacturedMaterial_idManufacturedMaterial=idManufacturedMaterial and Material_idMaterial=idMaterial and Entity_idEntity=idEntity and idContainer=?;";
    public static final String SELECT_CONTAINERS = "select * from Container inner join ManufacturedMaterial inner join Material inner join Entity where ManufacturedMaterial_idManufacturedMaterial=idManufacturedMaterial and Material_idMaterial=idMaterial and Entity_idEntity=idEntity;";
    public static final String INSERT_CODE = "insert into Codes_has_Container (Codes_idCodes,Container_idContainer,type) values (?,?,?);";
    public static final String UPDATE_CODE = "update Codes_has_Container set Codes_idCodes=? where Container_idContainer=? and type=?;";
    public static final String DELETE_CODE = "delete from Codes_has_Container where Container_idContainer=? and type=?;";
    public static final String SELECT_CODES = "Select * from Codes_has_Container inner join Codes where Codes_idCodes=idCodes and Container_idContainer=?;";

    //------------------------------------------------------------------------------------------------------------------
    //CRUD methods
    //------------------------------------------------------------------------------------------------------------------



    @Override
    /**
     * Insert Container method.
     * @param Container container
     */
    public void insertContainer(Container container) throws ContainerExistsException, ManufacturedMaterialExistsException, MaterialExistsException, EntityExistsException {

        try {

            SpringServices.getManufacturedMaterialDAO().insertManufacturedMaterial(container);

            getJdbcTemplate().update(INSERT,
                    container.getIdContainer(),
                    container.getCapacityQuantity(),
                    container.getHeightQuantity(),
                    container.getDiameterQuantity(),
                    container.getBarrierDeltaQuantity(),
                    container.getBottomDeltaQuantity(),
                    container.getIdManufacturedMaterial());
            
            //Codes
            
            getJdbcTemplate().update(INSERT_CODE,
                    container.getCapTypeCode().getId(),
                    container.getIdContainer(),
                    container.getCapTypeCode().getType());

            getJdbcTemplate().update(INSERT_CODE,
                    container.getSeparatorTypeCode().getId(),
                    container.getIdContainer(),
                    container.getSeparatorTypeCode().getType());

        }catch (DataIntegrityViolationException e){
            throw new ContainerExistsException(e);
        }
    }


    @Override
    /**
     * Update Container method.
     * @param Container container
     */
    public void updateContainer(Container container) throws ContainerExistsException, ContainerNotExistsException, ManufacturedMaterialExistsException, ManufacturedMaterialNotExistsException, MaterialExistsException, MaterialNotExistsException, EntityNotExistsException, EntityExistsException, CodeExistsException, CodeNotExistsException {

        if (isDifferent(container)){

            try {

                SpringServices.getManufacturedMaterialDAO().updateManufacturedMaterial(container);

                int rows = getJdbcTemplate().update(UPDATE,
                        container.getCapacityQuantity(),
                        container.getHeightQuantity(),
                        container.getDiameterQuantity(),
                        container.getBarrierDeltaQuantity(),
                        container.getBottomDeltaQuantity(),
                        container.getIdContainer());

                if (rows==0){
                    throw new ContainerNotExistsException();
                }
                updateCodes(container);
            }catch (DataIntegrityViolationException e){
                throw new ContainerExistsException(e);
            }
        } else {
            SpringServices.getManufacturedMaterialDAO().updateManufacturedMaterial(container);
            updateCodes(container);
        }

    }

    @Override
    /**
     * Delete Container method
     * @param String id
     */
    public void deleteContainer(String id) throws ContainerNotExistsException, ContainerCannotBeDeletedException, ManufacturedMaterialCannotBeDeletedException, MaterialNotExistsException, EntityCannotBeDeletedException, MaterialCannotBeDeletedException, ManufacturedMaterialNotExistsException, EntityNotExistsException, CodeNotExistsException {
        try {
            Container container = getContainer(id);

            getJdbcTemplate().update(DELETE_CODE,id);

            int rows = getJdbcTemplate().update(DELETE, id);

            if (rows==0){
                throw new ContainerNotExistsException();
            }

            SpringServices.getManufacturedMaterialDAO().deleteManufacturedMaterial(container.getIdManufacturedMaterial());

        }catch (DataIntegrityViolationException e){
            throw new ContainerCannotBeDeletedException(e);
        }
    }

    @Override
    /**
     *get one Container method.
     * @param String id.
     */
    public Container getContainer(String id) throws ContainerNotExistsException, CodeNotExistsException {
        try {

            Container container = getJdbcTemplate().queryForObject(SELECT_CONTAINER,new ContainerRowMapper(),id);

            setContainerCodes(container, getContainerCodes(id));

            return container;
        }catch (EmptyResultDataAccessException e){
            throw new ContainerNotExistsException(e);
        }
    }

    /**
     * get
     * @return
     * @throws ContainerNotExistsException
     */
    @Override
    public List<Container> getContainers() throws ContainerNotExistsException, CodeNotExistsException {
        try {
            List<Container> containers = getJdbcTemplate().query(SELECT_CONTAINERS, new ContainerRowMapper());

            for (Container container:containers){
                setContainerCodes(container, getContainerCodes(container.getIdContainer()));
                containers.set(containers.indexOf(container),container);
            }

            return containers;

        }catch (EmptyResultDataAccessException e){
            throw new ContainerNotExistsException(e);
        }


    }

    /**
     * Get all codes of an Container.
     * @param id
     * @return
     */
    public List<Code> getContainerCodes(String id) throws CodeNotExistsException {
        try{

            List<Code> codes = getJdbcTemplate().query(SELECT_CODES, new CodeRowMapper(), id);
            return codes;

        }catch (EmptyResultDataAccessException e){
            throw new CodeNotExistsException(e);
        }
    }

    /**
     * Set Codes to Container
     * @param container
     * @param codes
     * @return
     */
    public void setContainerCodes(Container container, List<Code> codes) throws CodeNotExistsException {

        SpringServices.getMaterialDAO().setMaterialCodes(container,
                SpringServices.getMaterialDAO().getMaterialCodes(container.getIdMaterial()));

        for (Code code : codes) {

            String typeCode = code.getType();

            if (typeCode.equals("capTypeCap")) {
                container.setCapTypeCode(code);

            } else if (typeCode.equals("separatorTypeCode")) {
                container.setSeparatorTypeCode(code);

            }

        }
    }

    private boolean isDifferent(Container container) throws ContainerNotExistsException, CodeNotExistsException {
        Container oldContainer = getContainer(container.getIdContainer());

        int attrDifferent = 0;

        if (!container.getCapacityQuantity().equals(oldContainer.getCapacityQuantity())){
            attrDifferent++;
        }
        if (!container.getHeightQuantity().equals(oldContainer.getHeightQuantity())){
            attrDifferent++;
        }
        if (!container.getDiameterQuantity().equals(oldContainer.getDiameterQuantity())){
            attrDifferent++;
        }
        if (!container.getBarrierDeltaQuantity().equals(oldContainer.getBarrierDeltaQuantity())){
            attrDifferent++;
        }
        if (!container.getBottomDeltaQuantity().equals(oldContainer.getBottomDeltaQuantity())){
            attrDifferent++;
        }

        return attrDifferent!=0;
    }

    private void updateCodes(Container container) throws CodeNotExistsException, CodeExistsException {
        List<Code> oldCodes = getContainerCodes(container.getIdContainer());

        for (Code code:oldCodes){
            try {
                if (code.getType().equalsIgnoreCase(container.getCapTypeCode().getType())){
                    if (!code.getId().equals(container.getCapTypeCode().getId())){
                        getJdbcTemplate().update(UPDATE_CODE,
                                container.getCapTypeCode().getId(),
                                container.getIdContainer(),
                                container.getCapTypeCode().getType());
                    }
                }else if (code.getType().equalsIgnoreCase(container.getSeparatorTypeCode().getType())){
                    if (!code.getId().equals(container.getSeparatorTypeCode().getId())){
                        getJdbcTemplate().update(UPDATE_CODE,
                                container.getSeparatorTypeCode().getId(),
                                container.getIdContainer(),
                                container.getSeparatorTypeCode().getType());
                    }
                }
            }catch (DataIntegrityViolationException e){
                throw new CodeExistsException(e);
            }
        }
    }

}
