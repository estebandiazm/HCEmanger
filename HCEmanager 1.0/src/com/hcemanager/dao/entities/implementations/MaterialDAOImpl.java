package com.hcemanager.dao.entities.implementations;

import com.hcemanager.dao.codes.CodeRowMapper;
import com.hcemanager.dao.entities.rowMappers.MaterialRowMapper;
import com.hcemanager.dao.entities.interfaces.MaterialDAO;
import com.hcemanager.dao.springServices.SpringServices;
import com.hcemanager.exceptions.codes.CodeExistsException;
import com.hcemanager.exceptions.codes.CodeNotExistsException;
import com.hcemanager.exceptions.entities.*;
import com.hcemanager.models.codes.Code;
import com.hcemanager.models.entities.Material;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.util.List;

/**
 * @author daniel, juan.
 */
public class MaterialDAOImpl extends JdbcDaoSupport implements MaterialDAO {

    //------------------------------------------------------------------------------------------------------------------
    //SQL sentence constants.
    //------------------------------------------------------------------------------------------------------------------

    public static final String INSERT = "insert into Material (idMaterial, Entity_idEntity) values (?,?)";
    //public static final String UPDATE = "update Material set where idMaterial=?";
    public static final String DELETE = "delete from Material where idMaterial=?";
    public static final String SELECT_MATERIAL = "select * from Material inner join Entity where Entity_idEntity=idEntity and idMaterial=?";
    public static final String SELECT_MATERIALS = "select * from Material inner join Entity where Entity_idEntity=idEntity";
    public static final String INSERT_CODE = "insert into Codes_has_Material(Codes_idCodes,Material_idMaterial,type) values (?,?,?);";
    public static final String UPDATE_CODE = "update Codes_has_Material set Codes_idCodes = ? where Material_idMaterial=? and type=?;";
    public static final String DELETE_CODE = "delete from Codes_has_Material where Material_idMaterial=?";
    public static final String SELECT_CODES = "select * from Codes_has_Material inner join Codes where Codes_idCodes=idCodes and Material_idMaterial=?";



    //------------------------------------------------------------------------------------------------------------------
    //CRUD methods
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Insert an Material.
     * @param material
     * @throws MaterialExistsException
     */
    @Override
    public void insertMaterial(Material material) throws MaterialExistsException, EntityExistsException {
        try {

            SpringServices.getEntityDAO().insertEntity(material);

            getJdbcTemplate().update(INSERT,
                    material.getIdMaterial(),
                    material.getId());

            //Codes

            getJdbcTemplate().update(INSERT_CODE,
                    material.getFormCode().getId(),
                    material.getIdMaterial(),
                    material.getFormCode().getType());

        }catch (DataIntegrityViolationException e){
            throw new MaterialExistsException(e);
        }
    }

    /**
     * Update an Material.
     * @param material
     * @throws MaterialNotExistsException
     * @throws MaterialExistsException
     */
    @Override
    public void updateMaterial(Material material) throws MaterialNotExistsException, MaterialExistsException, EntityNotExistsException, EntityExistsException, CodeExistsException, CodeNotExistsException {

        try {

            SpringServices.getEntityDAO().updateEntity(material);

            //Codes

            updateCodes(material);

        }catch (DataIntegrityViolationException e){
            throw new MaterialExistsException(e);
        }
    }

    /**
     * Delete an Material.
     * @param id
     * @throws MaterialNotExistsException
     * @throws com.hcemanager.exceptions.entities.MaterialCannotBeDeletedException
     */
    @Override
    public void deleteMaterial(String id) throws MaterialNotExistsException, MaterialCannotBeDeletedException, EntityCannotBeDeletedException, EntityNotExistsException, CodeNotExistsException {
        try {

            Material material = getMaterial(id);
            getJdbcTemplate().update(DELETE_CODE,id);

            int rows = getJdbcTemplate().update(DELETE, id);

            if (rows==0){
                throw new MaterialNotExistsException();
            }

            SpringServices.getEntityDAO().deleteEntity(material.getId());

        }catch (DataIntegrityViolationException e){
            throw new MaterialCannotBeDeletedException(e);
        }
    }

    /**
     * Get an Material.
     * @param id
     * @return Material
     * @throws MaterialNotExistsException
     */
    @Override
    public Material getMaterial(String id) throws MaterialNotExistsException, CodeNotExistsException {
        try {
            Material material = getJdbcTemplate().queryForObject(SELECT_MATERIAL, new MaterialRowMapper(), id);

            setMaterialCodes(material, getMaterialCodes(id));

            return material;
        }catch(EmptyResultDataAccessException e){
            throw new MaterialNotExistsException(e);
        }
    }

    /**
     * Get all Materials.
     * @return List<Materials>
     * @throws MaterialNotExistsException
     */
    @Override
    public List<Material> getMaterials() throws MaterialNotExistsException, CodeNotExistsException {
        try {
            List<Material> materials = getJdbcTemplate().query(SELECT_MATERIALS, new MaterialRowMapper());

            for (Material material:materials){
                setMaterialCodes(material, getMaterialCodes(material.getIdMaterial()));
                materials.set(materials.indexOf(material),material);
            }

            return materials;
        }catch (EmptyResultDataAccessException e){
            throw new MaterialNotExistsException(e);
        }
    }

    /**
     * Get Codes of an Material by id
     * @param id
     * @return
     * @throws CodeNotExistsException
     */
    @Override
    public List<Code> getMaterialCodes(String id) throws CodeNotExistsException {
        try {
            List<Code> codes = getJdbcTemplate().query(SELECT_CODES, new CodeRowMapper(), id);
            return codes;
        }catch (EmptyResultDataAccessException e){
            throw new CodeNotExistsException(e);
        }
    }

    /**
     * Set Codes to Material.
     * @param material
     * @param codes
     * @return
     */
    @Override
    public void setMaterialCodes(Material material, List<Code> codes) throws CodeNotExistsException {

        SpringServices.getEntityDAO().setEntityCodes(material,
                SpringServices.getEntityDAO().getEntityCodes(material.getId()));

        for (Code code:codes){

            String typeCode = code.getType();

            if (typeCode.equals("formCode")){
                material.setFormCode(code);
            }
        }
    }

    private void updateCodes(Material material) throws CodeNotExistsException, CodeExistsException {
        List<Code> oldCodes = getMaterialCodes(material.getIdMaterial());

        for (Code code:oldCodes){
            try {
                if (code.getType().equalsIgnoreCase(material.getFormCode().getType())){
                    if (!code.getId().equals(material.getFormCode().getId())){
                        getJdbcTemplate().update(UPDATE_CODE,
                                material.getFormCode().getId(),
                                material.getIdMaterial(),
                                material.getFormCode().getType());
                    }
                }
            }catch (DataIntegrityViolationException e){
                throw new CodeExistsException(e);
            }
        }
    }
}
