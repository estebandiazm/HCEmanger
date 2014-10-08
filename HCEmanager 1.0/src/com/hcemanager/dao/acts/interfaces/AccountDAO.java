package com.hcemanager.dao.acts.interfaces;

import com.hcemanager.exceptions.acts.*;
import com.hcemanager.exceptions.codes.CodeExistsException;
import com.hcemanager.exceptions.codes.CodeIsNotValidException;
import com.hcemanager.exceptions.codes.CodeNotExistsException;
import com.hcemanager.models.acts.Account;

import java.util.List;

/**
 * This interface define the Account DAO methods
 * @author daniel, juan.
 */
public interface AccountDAO {

    //------------------------------------------------------------------------------------------------------------------
    //CRUD methods
    //------------------------------------------------------------------------------------------------------------------

    public void insertAccount(Account account) throws AccountExistsException, ActExistsException, CodeExistsException;
    public void updateAccount(Account account) throws AccountNotExistsException, AccountExistsException, ActNotExistsException, ActExistsException, CodeNotExistsException, CodeIsNotValidException, CodeExistsException;
    public void deleteAccount(String id) throws AccountNotExistsException, AccountCannotBeDeleted, ActNotExistsException, ActCannotBeDeleted, CodeIsNotValidException, CodeNotExistsException;
    public Account getAccount(String id) throws AccountNotExistsException, CodeNotExistsException, CodeIsNotValidException;
    public List<Account> getAccounts() throws AccountNotExistsException, CodeNotExistsException, CodeIsNotValidException;
}
