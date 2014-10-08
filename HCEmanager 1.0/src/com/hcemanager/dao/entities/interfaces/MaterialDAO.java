package com.hcemanager.dao.entities.interfaces;

import com.hcemanager.exceptions.codes.CodeExistsException;
import com.hcemanager.exceptions.codes.CodeNotExistsException;
import com.hcemanager.exceptions.entities.*;
import com.hcemanager.models.codes.Code;
import com.hcemanager.models.entities.Material;

import java.util.List;

/**
 * @author daniel, juan.
 */
public interface MaterialDAO {

   public void insertMaterial(Material material) throws MaterialExistsException, EntityExistsException;
   public void updateMaterial(Material material) throws MaterialNotExistsException, MaterialExistsException, EntityNotExistsException, EntityExistsException, CodeExistsException, CodeNotExistsException;
   public void deleteMaterial(String id) throws MaterialNotExistsException, MaterialCannotBeDeletedException, EntityCannotBeDeletedException, EntityNotExistsException, CodeNotExistsException;
   public Material getMaterial(String id) throws MaterialNotExistsException, CodeNotExistsException;
   public List<Material> getMaterials() throws MaterialNotExistsException, CodeNotExistsException;
   public List<Code> getMaterialCodes(String id) throws CodeNotExistsException;
   public void setMaterialCodes(Material material, List<Code> codes) throws CodeNotExistsException;

}
