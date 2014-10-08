package com.hcemanager.dao.entities.interfaces;

import java.util.List;

import com.hcemanager.exceptions.codes.CodeExistsException;
import com.hcemanager.exceptions.codes.CodeNotExistsException;
import com.hcemanager.exceptions.entities.*;
import com.hcemanager.models.codes.Code;
import com.hcemanager.models.entities.Person;

/**
 * @author Daniel Bellon & Juan Diaz
 */
public interface PersonDAO {

    public void insertPerson(Person person) throws PersonExistException, LivingSubjectExistsException, EntityExistsException;
    public void updatePerson(Person person) throws PersonNotExistException, PersonExistException,
            LivingSubjectExistsException, EntityExistsException, LivingSubjectNotExistsException, EntityNotExistsException,
            CodeNotExistsException, CodeExistsException;
    public void deletePerson(String id) throws PersonNotExistException, PersonCannotBeDeletedException,
            LivingSubjectCannotBeDeletedException, EntityCannotBeDeletedException, LivingSubjectNotExistsException,
            EntityNotExistsException, CodeNotExistsException;
    public Person getPerson(String id) throws PersonNotExistException, CodeNotExistsException;
    public List<Person> getPersons() throws PersonNotExistException, CodeNotExistsException;
    public void setPersonCodes(Person person,List<Code> codes) throws CodeNotExistsException;
    public List<Code> getPersonCodes(String id) throws CodeNotExistsException;

}
