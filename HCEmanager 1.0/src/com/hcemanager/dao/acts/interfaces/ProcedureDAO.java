package com.hcemanager.dao.acts.interfaces;

import com.hcemanager.exceptions.acts.*;
import com.hcemanager.exceptions.codes.CodeExistsException;
import com.hcemanager.exceptions.codes.CodeIsNotValidException;
import com.hcemanager.exceptions.codes.CodeNotExistsException;
import com.hcemanager.models.acts.Procedure;

import java.util.List;

/**
 * This interface define the Procedure DAO methods
 * @author daniel, esteban.
 */
public interface ProcedureDAO {

    public void insertProcedure(Procedure procedure) throws ProcedureExistsException, ActExistsException, CodeExistsException;
    public void updateProcedure(Procedure procedure) throws ProcedureExistsException, ProcedureNotExistsException, ActNotExistsException, ActExistsException, CodeIsNotValidException, CodeNotExistsException, CodeExistsException;
    public void deleteProcedure(String id) throws ProcedureNotExistsException, ProcedureCannotBeDeletedException, ActNotExistsException, ActCannotBeDeleted, CodeIsNotValidException, CodeNotExistsException;
    public Procedure getProcedure(String id) throws ProcedureNotExistsException, CodeNotExistsException, CodeIsNotValidException;
    public List<Procedure> getProcedures() throws ProcedureNotExistsException, CodeNotExistsException, CodeIsNotValidException;
}
