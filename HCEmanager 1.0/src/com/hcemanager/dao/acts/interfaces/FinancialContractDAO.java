package com.hcemanager.dao.acts.interfaces;

import com.hcemanager.exceptions.acts.*;
import com.hcemanager.exceptions.codes.CodeExistsException;
import com.hcemanager.exceptions.codes.CodeIsNotValidException;
import com.hcemanager.exceptions.codes.CodeNotExistsException;
import com.hcemanager.models.acts.FinancialContract;

import java.util.List;

/**
 * This interface define the Financial contracts DAO methods
 * @author daniel, esteban.
 */
public interface FinancialContractDAO {

    public void insertFinancialContract(FinancialContract financialContract) throws FinancialContractExistsException, ActExistsException, CodeExistsException;
    public void updateFinancialContract(FinancialContract financialContract) throws FinancialContractExistsException, FinancialContractNotExistsException, ActNotExistsException, ActExistsException, CodeIsNotValidException, CodeNotExistsException, CodeExistsException;
    public void deleteFinancialContract(String id) throws FinancialContractNotExistsException, FinancialContractCannotBeDeletedException, ActNotExistsException, ActCannotBeDeleted, CodeIsNotValidException, CodeNotExistsException;
    public FinancialContract getFinancialContract(String id) throws FinancialContractNotExistsException, CodeNotExistsException, CodeIsNotValidException;
    public List<FinancialContract> getFinancialContracts() throws FinancialContractNotExistsException, CodeNotExistsException, CodeIsNotValidException;
}
