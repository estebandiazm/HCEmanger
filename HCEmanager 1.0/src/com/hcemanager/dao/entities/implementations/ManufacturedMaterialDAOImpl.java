package com.hcemanager.dao.entities.implementations;

import com.hcemanager.dao.entities.interfaces.ManufacturedMaterialDAO;
import com.hcemanager.dao.entities.rowMappers.ManufactureMaterialRowMapper;
import com.hcemanager.dao.springServices.SpringServices;
import com.hcemanager.exceptions.codes.CodeExistsException;
import com.hcemanager.exceptions.codes.CodeNotExistsException;
import com.hcemanager.exceptions.entities.*;
import com.hcemanager.models.entities.ManufacturedMaterial;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.util.List;

/**
 * @author daniel, juan.
 */
public class ManufacturedMaterialDAOImpl extends JdbcDaoSupport implements ManufacturedMaterialDAO {

    //------------------------------------------------------------------------------------------------------------------
    //SQL sentence constants.
    //------------------------------------------------------------------------------------------------------------------

    public static final String INSERT = "insert into ManufacturedMaterial (idManufacturedMaterial, lotNumberText, expirationTime,stabilityTime, Material_idMaterial) values (?,?,?,?,?)";
    public static final String UPDATE = "update ManufacturedMaterial set lotNumberText=?, expirationTime=?, stabilityTime=? where idManufacturedMaterial=?";
    public static final String DELETE = "delete from ManufacturedMaterial where idManufacturedMaterial=?";
    public static final String SELECT_MANUFACTURED_MATERIAL = "select * from ManufacturedMaterial inner join Material inner join Entity where Material_idMaterial=idMaterial and Entity_idEntity=idEntity and idManufacturedMaterial=?";
    public static final String SELECT_MANUFACTURED_MATERIALS = "select * from ManufacturedMaterial inner join Material inner join Entity where Material_idMaterial=idMaterial and Entity_idEntity=idEntity;";


    //------------------------------------------------------------------------------------------------------------------
    //CRUD methods
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Insert an ManufacturedMaterial.
     * @param manufacturedMaterial
     * @throws com.hcemanager.exceptions.entities.ManufacturedMaterialExistsException
     */
    @Override
    public void insertManufacturedMaterial(ManufacturedMaterial manufacturedMaterial) throws ManufacturedMaterialExistsException, MaterialExistsException, EntityExistsException {
        try {

            SpringServices.getMaterialDAO().insertMaterial(manufacturedMaterial);

            getJdbcTemplate().update(INSERT,
                    manufacturedMaterial.getIdManufacturedMaterial(),
                    manufacturedMaterial.getLotNumberText(),
                    manufacturedMaterial.getExpirationTime(),
                    manufacturedMaterial.getStabilityTime(),
                    manufacturedMaterial.getIdMaterial());

        }catch (DataIntegrityViolationException e){
            throw new ManufacturedMaterialExistsException(e);
        }
    }

    /**
     * Update an ManufactureMaterial.
     * @param manufacturedMaterial
     * @throws com.hcemanager.exceptions.entities.ManufacturedMaterialNotExistsException
     * @throws com.hcemanager.exceptions.entities.ManufacturedMaterialExistsException
     */
    @Override
    public void updateManufacturedMaterial(ManufacturedMaterial manufacturedMaterial) throws ManufacturedMaterialNotExistsException, ManufacturedMaterialExistsException, MaterialExistsException, MaterialNotExistsException, EntityNotExistsException, EntityExistsException, CodeNotExistsException, CodeExistsException {

        if (isDifferent(manufacturedMaterial)){

            try {

                SpringServices.getMaterialDAO().updateMaterial(manufacturedMaterial);

                int rows = getJdbcTemplate().update(UPDATE,
                        manufacturedMaterial.getLotNumberText(),
                        manufacturedMaterial.getExpirationTime(),
                        manufacturedMaterial.getStabilityTime(),
                        manufacturedMaterial.getIdManufacturedMaterial());

                if (rows==0){
                    throw new ManufacturedMaterialNotExistsException();
                }
            }catch (DataIntegrityViolationException e){
                throw new ManufacturedMaterialExistsException(e);
            }
        } else {
            SpringServices.getMaterialDAO().updateMaterial(manufacturedMaterial);
        }

    }

    /**
     * Delete an ManufactureMaterial.
     * @param id
     * @throws com.hcemanager.exceptions.entities.ManufacturedMaterialNotExistsException
     * @throws com.hcemanager.exceptions.entities.ManufacturedMaterialCannotBeDeletedException
     */
    @Override
    public void deleteManufacturedMaterial(String id) throws ManufacturedMaterialNotExistsException, ManufacturedMaterialCannotBeDeletedException, MaterialCannotBeDeletedException, MaterialNotExistsException, EntityCannotBeDeletedException, EntityNotExistsException, CodeNotExistsException {
        try {

            ManufacturedMaterial manufacturedMaterial = getManufacturedMaterial(id);

            int rows = getJdbcTemplate().update(DELETE, id);

            if (rows==0){
                throw new ManufacturedMaterialNotExistsException();
            }

            SpringServices.getMaterialDAO().deleteMaterial(manufacturedMaterial.getIdMaterial());

        }catch (DataIntegrityViolationException e){
            throw new ManufacturedMaterialCannotBeDeletedException(e);
        }
    }

    /**
     * Get an ManufactureMaterial.
     * @param id
     * @return ManufactureMaterial.
     * @throws com.hcemanager.exceptions.entities.ManufacturedMaterialNotExistsException
     */
    @Override
    public ManufacturedMaterial getManufacturedMaterial(String id) throws ManufacturedMaterialNotExistsException, CodeNotExistsException {
        try {
            ManufacturedMaterial manufacturedMaterial = getJdbcTemplate().queryForObject(SELECT_MANUFACTURED_MATERIAL, new ManufactureMaterialRowMapper(), id);
            SpringServices.getMaterialDAO().setMaterialCodes(manufacturedMaterial,
                    SpringServices.getMaterialDAO().getMaterialCodes(manufacturedMaterial.getIdMaterial()));
            return  manufacturedMaterial;
        }catch (EmptyResultDataAccessException e){
            throw new ManufacturedMaterialNotExistsException(e);
        }
    }

    @Override
    public List<ManufacturedMaterial> getManufacturedMaterials() throws ManufacturedMaterialNotExistsException, CodeNotExistsException {
        try {

            List<ManufacturedMaterial> manufacturedMaterials = getJdbcTemplate().query(SELECT_MANUFACTURED_MATERIALS, new ManufactureMaterialRowMapper());
            for (ManufacturedMaterial manufacturedMaterial:manufacturedMaterials){
                SpringServices.getMaterialDAO().setMaterialCodes(manufacturedMaterial,
                        SpringServices.getMaterialDAO().getMaterialCodes(manufacturedMaterial.getIdMaterial()));
                manufacturedMaterials.set(manufacturedMaterials.indexOf(manufacturedMaterial),manufacturedMaterial);
            }
            return manufacturedMaterials;
        }catch (EmptyResultDataAccessException e){
            throw new ManufacturedMaterialNotExistsException(e);
        }
    }

    private boolean isDifferent(ManufacturedMaterial manufacturedMaterial) throws CodeNotExistsException, ManufacturedMaterialNotExistsException {
        ManufacturedMaterial oldManufacturedMaterial = getManufacturedMaterial(manufacturedMaterial.getIdManufacturedMaterial());

        int attrDifferent=0;

        if (!manufacturedMaterial.getLotNumberText().equals(oldManufacturedMaterial.getLotNumberText())){
          attrDifferent++;
        }
        if (!manufacturedMaterial.getExpirationTime().equals(oldManufacturedMaterial.getExpirationTime())){
            attrDifferent++;
        }
        if (!manufacturedMaterial.getStabilityTime().equals(oldManufacturedMaterial.getStabilityTime())){
            attrDifferent++;
        }

        return attrDifferent!=0;
    }
}
