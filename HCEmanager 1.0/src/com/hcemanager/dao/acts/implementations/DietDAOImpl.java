package com.hcemanager.dao.acts.implementations;

import com.hcemanager.dao.acts.interfaces.ActDAO;
import com.hcemanager.dao.acts.interfaces.DietDAO;
import com.hcemanager.dao.acts.rowMappers.DietRowMapper;
import com.hcemanager.dao.springServices.SpringServices;
import com.hcemanager.exceptions.acts.*;
import com.hcemanager.exceptions.codes.CodeExistsException;
import com.hcemanager.exceptions.codes.CodeIsNotValidException;
import com.hcemanager.exceptions.codes.CodeNotExistsException;
import com.hcemanager.models.acts.Diet;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.util.List;

/**
 * @author daniel, esteban.
 */
public class DietDAOImpl extends JdbcDaoSupport implements DietDAO {

    // -----------------------------------------------------------------------------------------------------------------
    // SQL sentence constants
    // -----------------------------------------------------------------------------------------------------------------

    public static final String INSERT = "insert into Diet(idDiet,energyQuantity,carbohydrateQuantity,Supply_idSupply) values (?,?,?,?)";
    public static final String UPDATE = "update Diet set energyQuantity=?,carbohydrateQuantity=? where idDiet=?";
    public static final String DELETE = "delete from Diet where idDiet=?";
    public static final String SELECT_DIET = "select * from Diet inner join Supply inner join Act where Supply_idSupply=idSupply and Act_idAct = idAct and idDiet=?";
    public static final String SELECT_DIETS = "select * from Diet inner join Supply inner  join Act where Supply_idSupply=idSupply and Act_idAct = idAct";

    // -----------------------------------------------------------------------------------------------------------------
    // CRUD methods
    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Insert a diet in the db
     * @param diet a Diet object
     * @throws DietExistsException
     */
    @Override
    public void insertDiet(Diet diet) throws DietExistsException, SupplyExistsException, ActExistsException, CodeExistsException {
        try {

            SpringServices.getSupplyDAO().insertSupply(diet);

            getJdbcTemplate().update(INSERT,
                    diet.getIdDiet(),
                    diet.getEnergyQuantity(),
                    diet.getCarbohydrateQuantity(),
                    diet.getIdSupply());

        }catch (DataIntegrityViolationException e){
            throw new DietExistsException(e);
        }

    }

    /**
     * Update a diet in the db
     * @param diet a Diet object
     * @throws DietExistsException
     */
    @Override
    public void updateDiet(Diet diet) throws DietExistsException, DietNotExistsException, SupplyExistsException, SupplyNotExistsException, ActNotExistsException, ActExistsException, CodeIsNotValidException, CodeNotExistsException, CodeExistsException {
        if (isDifferent(diet)){
            try {

                SpringServices.getSupplyDAO().updateSupply(diet);

                int rows = getJdbcTemplate().update(UPDATE,
                        diet.getEnergyQuantity(),
                        diet.getCarbohydrateQuantity(),
                        diet.getIdDiet());
                if (rows==0){
                    throw new DietNotExistsException();
                }
            }catch (DataIntegrityViolationException e){
                throw new DietExistsException(e);
            }
        }else {
            SpringServices.getSupplyDAO().updateSupply(diet);
        }
    }

    /**
     * Delete a diet from the db
     * @param id the diet id
     * @throws DietNotExistsException
     * @throws DietCannotBeDeleted
     */
    @Override
    public void deleteDiet(String id) throws DietNotExistsException, DietCannotBeDeleted, SupplyNotExistsException, SupplyCannotBeDeletedException, ActNotExistsException, ActCannotBeDeleted, CodeIsNotValidException, CodeNotExistsException {
        try {

            int rows = getJdbcTemplate().update(DELETE, id);

            if (rows==0){
                throw new DietNotExistsException();
            }

            Diet diet = getDiet(id);
            SpringServices.getSupplyDAO().deleteSupply(diet.getIdSupply());

        }catch(DataIntegrityViolationException e){
            throw new DietCannotBeDeleted(e);
        }
    }

    /**
     * Get a Diet by id from the db
     * @param id the diet id
     * @return diet
     * @throws DietNotExistsException
     */
    @Override
    public Diet getDiet(String id) throws DietNotExistsException, CodeNotExistsException, CodeIsNotValidException {
        try {
            Diet diet = getJdbcTemplate().queryForObject(SELECT_DIET, new DietRowMapper(), id);
            ActDAO actDAO = SpringServices.getActDAO();
            actDAO.setActCodes(diet,actDAO.getActCodes(diet.getId()));
            return diet;

        }catch (EmptyResultDataAccessException e){
            throw new DietNotExistsException(e);
        }
    }

    /**
     * Get all the diets in the db
     * @return diets
     * @throws DietNotExistsException
     */
    @Override
    public List<Diet> getDiets() throws DietNotExistsException, CodeNotExistsException, CodeIsNotValidException {
        try {
            List<Diet> diets= getJdbcTemplate().query(SELECT_DIETS, new DietRowMapper());

            for (Diet diet:diets){
                ActDAO actDAO = SpringServices.getActDAO();
                actDAO.setActCodes(diet,actDAO.getActCodes(diet.getId()));
            }
            return diets;

        }catch (EmptyResultDataAccessException e){
            throw new DietNotExistsException(e);
        }
    }

    /**
     * Evaluate if the diet is different
     * @param diet a Diet object
     * @return true id the diet is different
     * @throws CodeIsNotValidException
     * @throws DietNotExistsException
     * @throws CodeNotExistsException
     */
    private boolean isDifferent(Diet diet) throws CodeIsNotValidException, DietNotExistsException, CodeNotExistsException {
        Diet oldDiet = getDiet(diet.getIdDiet());
        int differentAttrs=0;

        if (!diet.getEnergyQuantity().equalsIgnoreCase(oldDiet.getEnergyQuantity()))
            differentAttrs++;
        if (!diet.getCarbohydrateQuantity().equalsIgnoreCase(oldDiet.getCarbohydrateQuantity()))
            differentAttrs++;

        return differentAttrs!=0;
    }
}
