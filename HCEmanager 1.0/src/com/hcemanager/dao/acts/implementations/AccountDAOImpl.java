package com.hcemanager.dao.acts.implementations;

import com.hcemanager.dao.acts.interfaces.AccountDAO;
import com.hcemanager.dao.acts.interfaces.ActDAO;
import com.hcemanager.dao.acts.rowMappers.AccountRowMapper;
import com.hcemanager.dao.codes.CodeRowMapper;
import com.hcemanager.dao.springServices.SpringServices;
import com.hcemanager.exceptions.acts.*;
import com.hcemanager.exceptions.codes.CodeExistsException;
import com.hcemanager.exceptions.codes.CodeIsNotValidException;
import com.hcemanager.exceptions.codes.CodeNotExistsException;
import com.hcemanager.models.acts.Account;
import com.hcemanager.models.codes.Code;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.util.List;

/**
 * This class implements the AccountDAO interface and implements its methods
 * @author daniel, esteban.
 */
public class AccountDAOImpl extends JdbcDaoSupport implements AccountDAO {

    // -----------------------------------------------------------------------------------------------------------------
    // SQL sentences and arguments
    // -----------------------------------------------------------------------------------------------------------------

    public static final String INSERT = "insert into Account(idAccount,balanceAmt,interestRateQuantity,allowedBalanceQuantity,Act_idAct) values (?,?,?,?,?)";
    public static final String UPDATE = "update Account set balanceAmt=?,interestRateQuantity=?, allowedBalanceQuantity=? where idAccount=?";
    public static final String DELETE = "delete from Account where idAccount=?";
    public static final String SELECT_ACCOUNT = "select * from Account inner join Act where Act_idAct = idAct and idAccount=?";
    public static final String SELECT_ACCOUNTS = "select * from Account inner join  Act where  Act_idAct = idAct";
    public static final String INSERT_CODE = "insert into Account_has_Codes(Account_idAccount,Codes_idCodes,type) values (?,?,?)";
    public static final String UPDATE_CODE = "update Account_has_Codes set Codes_idCodes = ? where Account_idAccount =? and type=?";
    public static final String DELETE_CODES = "delete from Account_has_Codes where Account_idAccount=?";
    public static final String SELECT_CODES = "select * from Account_has_Codes inner join Codes where Codes_idCodes = idCodes and Account_idAccount=?";


    // -----------------------------------------------------------------------------------------------------------------
    // CRUD methods
    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Insert an account in tge db
     * @param account an Account object
     * @throws AccountExistsException
     */
    @Override
    public void insertAccount(Account account) throws AccountExistsException, ActExistsException, CodeExistsException {
        try {
            SpringServices.getActDAO().insertAct(account);

            getJdbcTemplate().update(INSERT,
                    account.getIdAccount(),
                    account.getBalanceAmt(),
                    account.getInterestRateQuantity(),
                    account.getAllowedBalanceQuantity(),
                    account.getId());
        }catch (DataIntegrityViolationException e){
            throw new AccountExistsException(e);
        }

        try {
            getJdbcTemplate().update(INSERT_CODE,
                    account.getIdAccount(),
                    account.getCurrencyCode().getId(),
                    "currencyCode");
        }catch (DataIntegrityViolationException e){
            throw new CodeExistsException(e);
        }
    }


    /**
     * Update an account
     * @param account an Account object
     * @throws AccountNotExistsException
     * @throws AccountExistsException
     */
    @Override
    public void updateAccount(Account account) throws AccountNotExistsException, AccountExistsException, ActNotExistsException, ActExistsException, CodeNotExistsException, CodeIsNotValidException, CodeExistsException {

        if (isDifferent(account)){
            try {
                SpringServices.getActDAO().updateAct(account);

                int rows = getJdbcTemplate().update(UPDATE,
                        account.getBalanceAmt(),
                        account.getInterestRateQuantity(),
                        account.getAllowedBalanceQuantity(),
                        account.getIdAccount());
                if (rows==0){
                    throw new AccountNotExistsException();
                }

                updateCodes(account);

            }catch (DataIntegrityViolationException e){
                throw new AccountExistsException(e);
            }
        }else{
            SpringServices.getActDAO().updateAct(account);
            updateCodes(account);
        }
    }

    /**
     * Delete an account by id
     * @param id the account id
     * @throws AccountNotExistsException
     * @throws AccountCannotBeDeleted
     */
    @Override
    public void deleteAccount(String id) throws AccountNotExistsException, AccountCannotBeDeleted, ActNotExistsException, ActCannotBeDeleted, CodeIsNotValidException, CodeNotExistsException {

        try {

            getJdbcTemplate().update(DELETE_CODES,id);

            int rows = getJdbcTemplate().update(DELETE, id);
            if (rows==0){
                throw new AccountNotExistsException();
            }

            Account account = getAccount(id);
            SpringServices.getActDAO().deleteAct(account.getId());

        }catch(DataIntegrityViolationException e){
            throw new AccountCannotBeDeleted(e);
        }
    }

    /**
     * Get an account by id
     * @param id the account id
     * @return account
     * @throws AccountNotExistsException
     */
    @Override
    public Account getAccount(String id) throws AccountNotExistsException, CodeNotExistsException, CodeIsNotValidException {
        try {

            Account account = getJdbcTemplate().queryForObject(SELECT_ACCOUNT,new AccountRowMapper(), id);
            ActDAO actDAO = SpringServices.getActDAO();
            actDAO.setActCodes(account, actDAO.getActCodes(account.getId()));
            setAccountCodes(account,getAccountCodes(id));
            return account;

        }catch (EmptyResultDataAccessException  e){
            throw new AccountNotExistsException(e);
        }

    }

    /**
     * Get all accounts in the db
     * @return accounts
     * @throws AccountNotExistsException
     */
    @Override
    public List<Account> getAccounts() throws AccountNotExistsException, CodeNotExistsException, CodeIsNotValidException {
        try {
            List<Account> accounts = getJdbcTemplate().query(SELECT_ACCOUNTS, new AccountRowMapper());

            for (Account account:accounts){

                ActDAO actDAO = SpringServices.getActDAO();
                actDAO.setActCodes(account, actDAO.getActCodes(account.getId()));
                setAccountCodes(account,getAccountCodes(account.getIdAccount()));
            }
            return accounts;

        }catch (EmptyResultDataAccessException  e){
            throw new AccountNotExistsException(e);
        }
    }

    /**
     * Get the account codes
     * @param id the account id
     * @return an account codes list
     * @throws CodeNotExistsException
     */
    public List<Code> getAccountCodes(String id) throws CodeNotExistsException {
        try {
            List<Code> codes = getJdbcTemplate().query(SELECT_CODES, new CodeRowMapper(), id);
            return codes;
        }catch (EmptyResultDataAccessException e){
            throw new CodeNotExistsException(e);
        }
    }

    /**
     * Set codes to an account
     * @param account an Account object
     * @param codes the account codes
     * @throws CodeIsNotValidException
     */
    public void setAccountCodes(Account account, List<Code>codes) throws CodeIsNotValidException {
        try {
            for (Code code:codes){
                String type=code.getType();

                if (type.equalsIgnoreCase("currencyCode")){
                    account.setCurrencyCode(code);
                }
            }
        }catch (Exception e){
            throw new CodeIsNotValidException(e);
        }
    }

    /**
     * Evaluate if the new account is different of the db account
     * @param account an Account object
     * @return true if the new account is different
     * @throws AccountNotExistsException
     * @throws CodeIsNotValidException
     * @throws CodeNotExistsException
     */
    private boolean isDifferent(Account account) throws AccountNotExistsException, CodeIsNotValidException, CodeNotExistsException {

        Account oldAccount = getAccount(account.getIdAccount());
        int differentAttrs=0;

        if (!account.getBalanceAmt().equalsIgnoreCase(oldAccount.getBalanceAmt()))
            differentAttrs++;
        if (!account.getInterestRateQuantity().equalsIgnoreCase(oldAccount.getInterestRateQuantity()))
            differentAttrs++;
        if (!account.getAllowedBalanceQuantity().equalsIgnoreCase(oldAccount.getAllowedBalanceQuantity()))
            differentAttrs++;

        return differentAttrs!=0;
    }

    /**
     * Update a list of account codes
     * @param account an Account object
     * @throws CodeNotExistsException
     * @throws CodeExistsException
     */
    private void updateCodes(Account account) throws CodeNotExistsException, CodeExistsException {
        List<Code> oldCodes = getAccountCodes(account.getIdAccount());

        for (Code code:oldCodes){
            if (code.getType().equalsIgnoreCase("currencyCode")) {
                if (!code.getId().equals(account.getCurrencyCode().getId())){
                    try {
                        getJdbcTemplate().update(UPDATE_CODE,account.getCurrencyCode().getId(),account.getIdAccount(),
                                "currencyCode");
                    }catch (DataIntegrityViolationException e){
                        throw new CodeExistsException(e);
                    }
                }
            }
        }
    }
}
