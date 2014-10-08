package com.hcemanager.dao.acts.interfaces;

import com.hcemanager.exceptions.acts.*;
import com.hcemanager.exceptions.codes.CodeExistsException;
import com.hcemanager.exceptions.codes.CodeIsNotValidException;
import com.hcemanager.exceptions.codes.CodeNotExistsException;
import com.hcemanager.models.acts.SubstanceAdministration;

import java.util.List;

/**
 * This interface define the substance administration DAO methods
 * @author daniel, esteban.
 */
public interface SubstanceAdministrationDAO {

    public void insertSubstanceAdministration(SubstanceAdministration substanceAdministration) throws SubstanceAdministrationExistsException, ActExistsException, CodeExistsException;
    public void updateSubstanceAdministration(SubstanceAdministration substanceAdministration) throws SubstanceAdministrationExistsException, SubstanceAdministrationNotExistsException, ActNotExistsException, ActExistsException, CodeIsNotValidException, CodeNotExistsException, CodeExistsException;
    public void deleteSubstanceAdministration(String id) throws SubstanceAdministrationNotExistsException, SubstanceAdministrationCannotBeDeletedException, ActNotExistsException, ActCannotBeDeleted, CodeIsNotValidException, CodeNotExistsException;
    public SubstanceAdministration getSubstanceAdministration(String id) throws SubstanceAdministrationNotExistsException, CodeNotExistsException, CodeIsNotValidException;
    public List<SubstanceAdministration> getSubstanceAdministrations() throws SubstanceAdministrationNotExistsException, CodeNotExistsException, CodeIsNotValidException;
}
