package com.hcemanager.dao.entities.interfaces;

import com.hcemanager.exceptions.codes.CodeExistsException;
import com.hcemanager.exceptions.codes.CodeNotExistsException;
import com.hcemanager.exceptions.entities.*;
import com.hcemanager.models.entities.NonPersonLivingSubject;

import java.util.List;

/**
 * @author daniel, juan.
 */
public interface NonPersonLivingSubjectDAO {

    public void insertNonPersonLivingSubject(NonPersonLivingSubject nonPersonLivingSubject) throws NonPersonLivingSubjectExistsException, LivingSubjectExistsException, EntityExistsException;
    public void updateNonPersonLivingSubject(NonPersonLivingSubject nonPersonLivingSubject) throws NonPersonLivingSubjectNotExistsException, NonPersonLivingSubjectExistsException, LivingSubjectExistsException, EntityExistsException, LivingSubjectNotExistsException, EntityNotExistsException, CodeNotExistsException, CodeExistsException;
    public void deleteNonPersonLivingSubject(String id) throws NonPersonLivingSubjectNotExistsException, NonPersonLivingSubjectCannotBeDeletedException, LivingSubjectCannotBeDeletedException, EntityCannotBeDeletedException, LivingSubjectNotExistsException, EntityNotExistsException, CodeNotExistsException;
    public NonPersonLivingSubject getNonPersonLivingSubject(String id) throws NonPersonLivingSubjectNotExistsException, CodeNotExistsException;
    public List<NonPersonLivingSubject> getNonPersonLivingSubjects() throws NonPersonLivingSubjectNotExistsException, CodeNotExistsException;

}
