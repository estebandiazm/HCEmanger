package com.hcemanager.dao.acts.implementations;

import com.hcemanager.dao.acts.interfaces.ActDAO;
import com.hcemanager.dao.acts.rowMappers.FinancialTransactionRowMapper;
import com.hcemanager.dao.acts.interfaces.FinancialTransactionDAO;
import com.hcemanager.dao.springServices.SpringServices;
import com.hcemanager.exceptions.acts.*;
import com.hcemanager.exceptions.codes.CodeExistsException;
import com.hcemanager.exceptions.codes.CodeIsNotValidException;
import com.hcemanager.exceptions.codes.CodeNotExistsException;
import com.hcemanager.models.acts.FinancialTransaction;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.util.List;

/**
 * @author daniel, esteban.
 */
public class FinancialTransactionDAOImpl extends JdbcDaoSupport implements FinancialTransactionDAO {

    // -----------------------------------------------------------------------------------------------------------------
    // SQL sentences
    // -----------------------------------------------------------------------------------------------------------------

    public static final String INSERT = "insert into FinancialTransaction(idFinancialTransaction,amt,creditExchangeRateQuantity,debitExchangeRateQuantity,Act_idAct) values (?,?,?,?,?)";
    public static final String UPDATE = "update FinancialTransaction set amt=?,creditExchangeRateQuantity=?,debitExchangeRateQuantity=? where idFinancialTransaction=?";
    public static final String DELETE = "delete from FinancialTransaction where idFinancialTransaction=?";
    public static final String SELECT_FINANCIAL_TRANSACTION = "select * from FinancialTransaction inner join Act where Act_idAct=idAct and  idFinancialTransacton=?";
    public static final String SELECT_FINANCIAL_TRANSACTIONS = "select * from FinancialTransaction inner join Act where Act_idAct=idAct";

    // -----------------------------------------------------------------------------------------------------------------
    // CRUD methods
    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Insert a financial transaction into the db
     * @param financialTransaction an Financial transaction object
     * @throws FinancialTransactionExistsException
     */
    @Override
    public void insertFinancialTransaction(FinancialTransaction financialTransaction) throws FinancialTransactionExistsException, ActExistsException, CodeExistsException {
        try {

            SpringServices.getActDAO().insertAct(financialTransaction);

            getJdbcTemplate().update(INSERT,
                    financialTransaction.getIdFinancialTransaction(),
                    financialTransaction.getAmt(),
                    financialTransaction.getCreditExchangeRateQuantity(),
                    financialTransaction.getDebitExchangeRateQuantity(),
                    financialTransaction.getId());

        }catch (DataIntegrityViolationException e){
            throw new FinancialTransactionExistsException(e);
        }

    }

    /**
     * Update a financial transaction into the db
     * @param financialTransaction an Financial transaction object
     * @throws FinancialTransactionExistsException
     */
    @Override
    public void updateFinancialTransaction(FinancialTransaction financialTransaction) throws FinancialTransactionExistsException, FinancialTransactionNotExistsException, ActNotExistsException, ActExistsException, CodeIsNotValidException, CodeNotExistsException, CodeExistsException {

        if (isDifferent(financialTransaction)){
            try {

                SpringServices.getActDAO().updateAct(financialTransaction);

                int rows = getJdbcTemplate().update(UPDATE,
                        financialTransaction.getAmt(),
                        financialTransaction.getCreditExchangeRateQuantity(),
                        financialTransaction.getDebitExchangeRateQuantity(),
                        financialTransaction.getIdFinancialTransaction());

                if (rows==0){
                    throw new FinancialTransactionNotExistsException();
                }
            }catch (DataIntegrityViolationException e){
                throw new FinancialTransactionExistsException(e);
            }
        }else {
            SpringServices.getActDAO().updateAct(financialTransaction);
        }
    }

    /**
     * Delete a financial transaction from the db
     * @param id the financial transaction id
     * @throws FinancialTransactionNotExistsException
     * @throws FinancialTransactionCannotBeDeletedException
     */
    @Override
    public void deleteFinancialTransaction(String id) throws FinancialTransactionNotExistsException, FinancialTransactionCannotBeDeletedException, ActNotExistsException, ActCannotBeDeleted, CodeIsNotValidException, CodeNotExistsException {
        try {

            int rows = getJdbcTemplate().update(DELETE,id);

            if (rows==0){
                throw new FinancialTransactionNotExistsException();
            }

            FinancialTransaction financialTransaction= getFinancialTransaction(id);
            SpringServices.getActDAO().deleteAct(financialTransaction.getId());

        }catch (DataIntegrityViolationException e){
            throw new FinancialTransactionCannotBeDeletedException(e);
        }
    }

    /**
     * Get a financial transaction from the db
     * @param id the financial transaction id
     * @return financialTransaction
     * @throws FinancialTransactionNotExistsException
     */
    @Override
    public FinancialTransaction getFinancialTransaction(String id) throws FinancialTransactionNotExistsException, CodeNotExistsException, CodeIsNotValidException {
        try {
            FinancialTransaction financialTransaction =
                    getJdbcTemplate().queryForObject(SELECT_FINANCIAL_TRANSACTION,
                            new FinancialTransactionRowMapper(), id);
            ActDAO actDAO = SpringServices.getActDAO();
            actDAO.setActCodes(financialTransaction,actDAO.getActCodes(financialTransaction.getId()));
            return financialTransaction;

        }catch (EmptyResultDataAccessException e){
            throw new FinancialTransactionNotExistsException(e);
        }
    }

    /**
     * Get all the financial transactions from the db
     * @return financial transactions
     * @throws FinancialTransactionNotExistsException
     */
    @Override
    public List<FinancialTransaction> getFinancialTransactions() throws FinancialTransactionNotExistsException, CodeNotExistsException, CodeIsNotValidException {
        try {
            List<FinancialTransaction> financialTransactions =
                    getJdbcTemplate().query(SELECT_FINANCIAL_TRANSACTIONS, new FinancialTransactionRowMapper());

            for (FinancialTransaction financialTransaction:financialTransactions){
                ActDAO actDAO = SpringServices.getActDAO();
                actDAO.setActCodes(financialTransaction,actDAO.getActCodes(financialTransaction.getId()));
            }
            return financialTransactions;

        }catch (EmptyResultDataAccessException e){
            throw new FinancialTransactionNotExistsException(e);
        }
    }

    /**
     * evaluate if the new financial transaction is different of the old financial transaction
     * @param financialTransaction an Financial transaction object
     * @return true if the financial transaction is different
     * @throws CodeIsNotValidException
     * @throws FinancialTransactionNotExistsException
     * @throws CodeNotExistsException
     */
    private boolean isDifferent(FinancialTransaction financialTransaction) throws CodeIsNotValidException, FinancialTransactionNotExistsException, CodeNotExistsException {
        FinancialTransaction oldFinancialTransaction = getFinancialTransaction(financialTransaction.getIdFinancialTransaction());
        int differentAttrs=0;

        if (!financialTransaction.getAmt().equalsIgnoreCase(oldFinancialTransaction.getAmt()))
            differentAttrs++;
        if (financialTransaction.getCreditExchangeRateQuantity()!= oldFinancialTransaction.getCreditExchangeRateQuantity())
            differentAttrs++;
        if (financialTransaction.getDebitExchangeRateQuantity()!=oldFinancialTransaction.getDebitExchangeRateQuantity())
            differentAttrs++;

        return differentAttrs!=0;
    }
}
