package com.hcemanager.dao.acts.interfaces;

import com.hcemanager.exceptions.acts.*;
import com.hcemanager.exceptions.codes.CodeExistsException;
import com.hcemanager.exceptions.codes.CodeIsNotValidException;
import com.hcemanager.exceptions.codes.CodeNotExistsException;
import com.hcemanager.models.acts.PublicHealthCase;

import java.util.List;

/**
 * This interface define the Public health case DAO methods
 * @author Daniel Bellon & Juan Diaz
 */
public interface PublicHealthCaseDAO {

    public void insertPublicHealthCase(PublicHealthCase publicHealthCase) throws PublicHealthCaseExistsException,
            ObservationExistsException, ActExistsException, CodeExistsException;
    public void updatePublicHealthCase(PublicHealthCase publicHealthCase) throws PublicHealthCaseExistsException,
            PublicHealthCaseNotExistsException, ActExistsException, ActNotExistsException, ObservationNotExistsException,
    ObservationExistsException, CodeIsNotValidException, CodeNotExistsException, CodeExistsException;
    public void deletePublicHealthCase(String id) throws PublicHealthCaseNotExistsException,
            PublicHealthCaseCannotBeDeletedException, ObservationCannotBeDeleteException, ActCannotBeDeleted,
            ObservationNotExistsException, ActNotExistsException, CodeIsNotValidException, CodeNotExistsException;
    public PublicHealthCase getPublicHealthCase(String id) throws PublicHealthCaseNotExistsException,
            CodeNotExistsException, CodeIsNotValidException;
    public List<PublicHealthCase> getPublicHealthCases() throws PublicHealthCaseNotExistsException,
            CodeNotExistsException, CodeIsNotValidException;
}
