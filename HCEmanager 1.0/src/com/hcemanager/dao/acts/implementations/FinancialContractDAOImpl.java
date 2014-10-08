package com.hcemanager.dao.acts.implementations;

import com.hcemanager.dao.acts.interfaces.ActDAO;
import com.hcemanager.dao.acts.rowMappers.FinancialContractRowMapper;
import com.hcemanager.dao.acts.interfaces.FinancialContractDAO;
import com.hcemanager.dao.codes.CodeRowMapper;
import com.hcemanager.dao.springServices.SpringServices;
import com.hcemanager.exceptions.acts.*;
import com.hcemanager.exceptions.codes.CodeExistsException;
import com.hcemanager.exceptions.codes.CodeIsNotValidException;
import com.hcemanager.exceptions.codes.CodeNotExistsException;
import com.hcemanager.models.acts.FinancialContract;
import com.hcemanager.models.codes.Code;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.util.List;

/**
 * @author daniel, esteban.
 */
public class FinancialContractDAOImpl extends JdbcDaoSupport implements FinancialContractDAO {

    // -----------------------------------------------------------------------------------------------------------------
    // SQL sentences
    // -----------------------------------------------------------------------------------------------------------------

    public static final String INSERT = "insert into FinancialContract(idFinancialContract,Act_idAct) value (?,?)";
    public static final String DELETE = "delete from FinancialContract where idFinancialContract=?";
    public static final String SELECT_FINANCIAL_CONTRACT = "select *from FinancialContract inner join Act where Act_idAct=idAct and  idFinancialContract=?";
    public static final String SELECT_FINANCIAL_CONTRACTS = "select *from FinancialContract inner join Act where Act_idAct=idAct";
    public static final String INSERT_CODE = "insert into FinancialContract_has_Codes (FinancialContract_idFinancialContract,Codes_idCodes,type) values (?,?,?)";
    public static final String UPDATE_CODE = "update FinancialContract_has_Codes set Codes_idCodes = ? where FinancialContract_idFinancialContract=? and type=? ";
    public static final String DELETE_CODES = "delete from FinancialContract_has_Codes where FinancialContract_idFinancialContract=?";
    public static final String SELECT_CODES = "select * from FinancialContract_has_Codes inner join Codes where Codes_idCodes=idCodes and FinancialContract_idFinancialContract=?";

    // -----------------------------------------------------------------------------------------------------------------
    // CRUD methods
    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Insert a Financial contract into the db
     * @param financialContract a FinancialContract object
     * @throws FinancialContractExistsException
     */
    @Override
    public void insertFinancialContract(FinancialContract financialContract) throws FinancialContractExistsException, ActExistsException, CodeExistsException {
        try {
            SpringServices.getActDAO().insertAct(financialContract);

            getJdbcTemplate().update(INSERT,
                    financialContract.getIdFinancialContract(),
                    financialContract.getId());
        }catch (DataIntegrityViolationException e){
            throw new FinancialContractExistsException(e);
        }
        try {
            getJdbcTemplate().update(INSERT_CODE,
                    financialContract.getIdFinancialContract(),
                    financialContract.getPaymentTermsCode().getId(),
                    "paymentTermsCode");
        }catch (DataIntegrityViolationException e){
            throw new CodeExistsException(e);
        }
    }

    /**
     * Update a Financial contract by id in the db
     * @param financialContract a FinancialContract object
     * @throws FinancialContractExistsException
     * @throws FinancialContractNotExistsException
     */
    @Override
    public void updateFinancialContract(FinancialContract financialContract) throws FinancialContractExistsException, FinancialContractNotExistsException, ActNotExistsException, ActExistsException, CodeIsNotValidException, CodeNotExistsException, CodeExistsException {
        SpringServices.getActDAO().updateAct(financialContract);
        updateCodes(financialContract);
    }

    /**
     * Delete a financial contract from the db
     * @param id the financial contract id
     * @throws FinancialContractNotExistsException
     * @throws FinancialContractCannotBeDeletedException
     */
    @Override
    public void deleteFinancialContract(String id) throws FinancialContractNotExistsException, FinancialContractCannotBeDeletedException, ActNotExistsException, ActCannotBeDeleted, CodeIsNotValidException, CodeNotExistsException {
        try {

            getJdbcTemplate().update(DELETE_CODES,id);

            int rows = getJdbcTemplate().update(DELETE, id);
            if (rows==0){
                throw new FinancialContractNotExistsException();
            }

            FinancialContract financialContract = getFinancialContract(id);
            SpringServices.getActDAO().deleteAct(financialContract.getId());

        }catch (DataIntegrityViolationException e){
            throw new FinancialContractCannotBeDeletedException(e);
        }
    }

    /**
     * Get a financial contract from the db
     * @param id the financial contract id
     * @return a financial contract
     * @throws FinancialContractNotExistsException
     */
    @Override
    public FinancialContract getFinancialContract(String id) throws FinancialContractNotExistsException, CodeNotExistsException, CodeIsNotValidException {
        try {
            FinancialContract financialContract =
                    getJdbcTemplate().queryForObject(SELECT_FINANCIAL_CONTRACT, new FinancialContractRowMapper(), id);
            ActDAO actDAO = SpringServices.getActDAO();
            actDAO.setActCodes(financialContract,actDAO.getActCodes(financialContract.getId()));
            setFinancialContractCodes(financialContract,getFinancialContractCodes(id));
            return financialContract;

        }catch (EmptyResultDataAccessException e){
            throw new FinancialContractNotExistsException(e);
        }
    }

    /**
     * Get all the financial contracts in the db
     * @return financial contracts
     * @throws FinancialContractNotExistsException
     */
    @Override
    public List<FinancialContract> getFinancialContracts() throws FinancialContractNotExistsException, CodeNotExistsException, CodeIsNotValidException {
        try {
            List<FinancialContract> financialContracts =
                    getJdbcTemplate().query(SELECT_FINANCIAL_CONTRACTS, new FinancialContractRowMapper());

            for (FinancialContract financialContract:financialContracts){
                ActDAO actDAO = SpringServices.getActDAO();
                actDAO.setActCodes(financialContract,actDAO.getActCodes(financialContract.getId()));
                setFinancialContractCodes(financialContract,getFinancialContractCodes(financialContract.getIdFinancialContract()));
            }
            return financialContracts;

        }catch (EmptyResultDataAccessException e){
            throw new FinancialContractNotExistsException(e);
        }
    }

    /**
     * Get a FinancialContract codes
     * @param id the financial contract id
     * @return a list with financialContract codes
     * @throws CodeNotExistsException
     */
    public List<Code> getFinancialContractCodes(String id) throws CodeNotExistsException {
        try {
            List<Code> codes = getJdbcTemplate().query(SELECT_CODES, new CodeRowMapper(), id);
            return codes;
        }catch (EmptyResultDataAccessException e){
            throw new CodeNotExistsException(e);
        }
    }

    /**
     * Set a financialContract codes
     * @param financialContract a FinancialContract object
     * @param codes a list with its codes
     * @throws CodeIsNotValidException
     */
    public void setFinancialContractCodes(FinancialContract financialContract,List<Code>codes) throws CodeIsNotValidException {
        try {
            for (Code code:codes){
                String type = code.getType();

                if (type.equalsIgnoreCase("paymentTermsCode")){
                    financialContract.setPaymentTermsCode(code);
                }
            }
        }catch (Exception e){
            throw new CodeIsNotValidException(e);
        }
    }

    /**
     * Update the financial contract codes
     * @param financialContract a FinancialContract object
     * @throws CodeNotExistsException
     * @throws CodeExistsException
     */
    private void updateCodes(FinancialContract financialContract) throws CodeNotExistsException, CodeExistsException {
        List<Code> oldCodes = getFinancialContractCodes(financialContract.getIdFinancialContract());

        for (Code code:oldCodes){
            if (code.getType().equalsIgnoreCase("paymentTermsCode")){
                if (!code.getId().equals(financialContract.getPaymentTermsCode().getId())){
                    try {
                        getJdbcTemplate().update(UPDATE_CODE,financialContract.getPaymentTermsCode().getId(),
                                financialContract.getIdFinancialContract(),"paymentTermsCode");
                    }catch (DataIntegrityViolationException e){
                        throw new CodeExistsException(e);
                    }
                }
            }
        }
    }
}
