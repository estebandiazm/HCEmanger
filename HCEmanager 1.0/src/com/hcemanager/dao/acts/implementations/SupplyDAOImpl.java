package com.hcemanager.dao.acts.implementations;

import com.hcemanager.dao.acts.interfaces.SupplyDAO;
import com.hcemanager.dao.acts.rowMappers.SupplyRowMapper;
import com.hcemanager.dao.springServices.SpringServices;
import com.hcemanager.exceptions.acts.*;
import com.hcemanager.exceptions.codes.CodeExistsException;
import com.hcemanager.exceptions.codes.CodeIsNotValidException;
import com.hcemanager.exceptions.codes.CodeNotExistsException;
import com.hcemanager.models.acts.Supply;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.util.List;

/**
 * @author daniel, esteban.
 */
public class SupplyDAOImpl extends JdbcDaoSupport implements SupplyDAO {

    // -----------------------------------------------------------------------------------------------------------------
    // SQL sentences
    // -----------------------------------------------------------------------------------------------------------------

    public static final String INSERT = "insert into Supply(idSupply,expectedUseTime,quantity,Act_idAct) values (?,?,?,?)";
    public static final String UPDATE = "update Supply set expectedUseTime=?,quantity=? where idSupply=?";
    public static final String DELETE = "delete from Supply where idSupply=?";
    public static final String SELECT_SUPPLY = "select * from Supply inner join Act where Act_idAct=idAct and  idSupply=?";
    public static final String SELECT_SUPPLIES = "select * from Supply inner join Act where Act_idAct=idAct";

    // -----------------------------------------------------------------------------------------------------------------
    // CRUD methods
    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Insert a supply into the db
     * @param supply a Supply object
     * @throws SupplyExistsException
     */
    @Override
    public void insertSupply(Supply supply) throws SupplyExistsException, ActExistsException, CodeExistsException {
        try {

            SpringServices.getActDAO().insertAct(supply);

            getJdbcTemplate().update(INSERT,
                    supply.getIdSupply(),
                    supply.getExpectedUseTime(),
                    supply.getQuantity(),
                    supply.getId());

        }catch (DataIntegrityViolationException e){
            throw new SupplyExistsException(e);
        }
    }

    /**
     * Update a Supply from the db
     * @param supply a Supply object
     * @throws SupplyExistsException
     * @throws SupplyNotExistsException
     */
    @Override
    public void updateSupply(Supply supply) throws SupplyExistsException, SupplyNotExistsException, ActNotExistsException, ActExistsException, CodeIsNotValidException, CodeNotExistsException, CodeExistsException {
        if (isDifferent(supply)){
            try {
                SpringServices.getActDAO().updateAct(supply);

                int rows = getJdbcTemplate().update(UPDATE,
                        supply.getExpectedUseTime(),
                        supply.getQuantity(),
                        supply.getIdSupply());
                if (rows==0){
                    throw new SupplyNotExistsException();
                }
            }catch (DataIntegrityViolationException e){
                throw new SupplyExistsException(e);
            }
        }else {
            SpringServices.getActDAO().updateAct(supply);
        }
    }

    /**
     * Delete a Supply from the db
     * @param id the supply id
     * @throws SupplyNotExistsException
     * @throws SupplyCannotBeDeletedException
     */
    @Override
    public void deleteSupply(String id) throws SupplyNotExistsException, SupplyCannotBeDeletedException, ActNotExistsException, ActCannotBeDeleted {
        try {

            int rows = getJdbcTemplate().update(DELETE,id);
            if (rows==0){
                throw new SupplyNotExistsException();
            }

            Supply supply = getSupply(id);
            SpringServices.getActDAO().deleteAct(supply.getId());

        }catch (DataIntegrityViolationException e){
            throw new SupplyCannotBeDeletedException(e);
        }
    }

    /**
     * Get a supply from the db
     * @param id the supply id
     * @return supply
     * @throws SupplyNotExistsException
     */
    @Override
    public Supply getSupply(String id) throws SupplyNotExistsException {
        try {
            Supply supply = getJdbcTemplate().queryForObject(SELECT_SUPPLY, new SupplyRowMapper(), id);
            return supply;

        }catch (EmptyResultDataAccessException e){
            throw new SupplyNotExistsException(e);

        }
    }

    /**
     * Get all the supplies in the db
     * @return supplies
     * @throws SupplyNotExistsException
     */
    @Override
    public List<Supply> getSupplies() throws SupplyNotExistsException {
        try {
            List<Supply> supplies = getJdbcTemplate().query(SELECT_SUPPLIES, new SupplyRowMapper());
            return supplies;

        }catch (EmptyResultDataAccessException e){
            throw new SupplyNotExistsException(e);

        }
    }

    /**
     * Evaluate if the new supply is different of the old supply
     * @param supply a Supply object
     * @return true if the supply is different
     * @throws SupplyNotExistsException
     */
    private boolean isDifferent(Supply supply) throws SupplyNotExistsException {
        Supply oldSupply = getSupply(supply.getIdSupply());
        int differentAttrs=0;

        if (!supply.getExpectedUseTime().equalsIgnoreCase(oldSupply.getExpectedUseTime()))
            differentAttrs++;
        if (!supply.getQuantity().equalsIgnoreCase(oldSupply.getQuantity()))
            differentAttrs++;

        return differentAttrs != 0;
    }

}
