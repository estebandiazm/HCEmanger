package com.hcemanager.dao.entities.interfaces;

import com.hcemanager.exceptions.codes.CodeExistsException;
import com.hcemanager.exceptions.codes.CodeNotExistsException;
import com.hcemanager.exceptions.entities.*;
import com.hcemanager.models.codes.Code;
import com.hcemanager.models.entities.LivingSubject;

import java.util.List;

/**
 * @author daniel, juan.
 */
public interface LivingSubjectDAO {

    public void insertLivingSubject(LivingSubject livingSubject) throws LivingSubjectExistsException, EntityExistsException;
    public void updateLivingSubject(LivingSubject livingSubject) throws LivingSubjectExistsException, LivingSubjectNotExistsException, EntityNotExistsException, EntityExistsException, CodeExistsException, CodeNotExistsException;
    public void deleteLivingSubject(String id) throws LivingSubjectCannotBeDeletedException, LivingSubjectNotExistsException, EntityNotExistsException, EntityCannotBeDeletedException, CodeNotExistsException;
    public LivingSubject getLivingSubject(String id) throws LivingSubjectNotExistsException, CodeNotExistsException;
    public List<LivingSubject> getLivingSubjects() throws LivingSubjectNotExistsException, CodeNotExistsException;
    public List<Code> getLivingSubjectCodes(String id) throws CodeNotExistsException;
    public void setLivingSubjectCodes(LivingSubject livingSubject, List<Code> codes) throws CodeNotExistsException;
}
