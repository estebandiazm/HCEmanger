package com.hcemanager.dao.acts.interfaces;

import com.hcemanager.exceptions.acts.*;
import com.hcemanager.exceptions.codes.CodeExistsException;
import com.hcemanager.exceptions.codes.CodeIsNotValidException;
import com.hcemanager.exceptions.codes.CodeNotExistsException;
import com.hcemanager.models.acts.FinancialTransaction;

import java.util.List;

/**
 * This interface define the Financial transaction DAO methods
 * @author daniel, esteban.
 */
public interface FinancialTransactionDAO {

    public void insertFinancialTransaction(FinancialTransaction financialTransaction) throws FinancialTransactionExistsException, ActExistsException, CodeExistsException;
    public void updateFinancialTransaction(FinancialTransaction financialTransaction) throws FinancialTransactionExistsException, FinancialTransactionNotExistsException, ActNotExistsException, ActExistsException, CodeIsNotValidException, CodeNotExistsException, CodeExistsException;
    public void deleteFinancialTransaction(String id) throws FinancialTransactionNotExistsException, FinancialTransactionCannotBeDeletedException, ActNotExistsException, ActCannotBeDeleted, CodeIsNotValidException, CodeNotExistsException;
    public FinancialTransaction getFinancialTransaction(String id) throws FinancialTransactionNotExistsException, CodeNotExistsException, CodeIsNotValidException;
    public List<FinancialTransaction> getFinancialTransactions() throws FinancialTransactionNotExistsException, CodeNotExistsException, CodeIsNotValidException;
}
