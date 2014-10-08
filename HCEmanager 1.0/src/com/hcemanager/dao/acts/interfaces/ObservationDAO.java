package com.hcemanager.dao.acts.interfaces;

import com.hcemanager.exceptions.acts.*;
import com.hcemanager.exceptions.codes.CodeExistsException;
import com.hcemanager.exceptions.codes.CodeIsNotValidException;
import com.hcemanager.exceptions.codes.CodeNotExistsException;
import com.hcemanager.models.acts.Observation;
import com.hcemanager.models.codes.Code;

import java.util.List;

/**
 * This interface define the Observation DAO methods
 * @author Daniel Bellon & Juan Diaz
 */
public interface ObservationDAO {

    public void insertObservation(Observation observation) throws ObservationExistsException,
            ActExistsException, CodeExistsException;
    public void updateObservation(Observation observation) throws ObservationExistsException,
            ObservationNotExistsException, ActNotExistsException, ActExistsException, CodeIsNotValidException,
            CodeNotExistsException, CodeExistsException;
    public void deleteObservation(String id) throws ObservationNotExistsException, ObservationCannotBeDeleteException,
            ActNotExistsException, ActCannotBeDeleted;
    public Observation getObservation(String id) throws ObservationNotExistsException;
    public List<Observation> getObservations() throws ObservationNotExistsException;
    public List<Code> getObservationCodes(String id) throws CodeNotExistsException;
    public void setObservationCodes(Observation observation,List<Code>codes) throws CodeIsNotValidException;
}
