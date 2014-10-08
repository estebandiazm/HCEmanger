package com.hcemanager.dao.entities.implementations;

import com.hcemanager.dao.codes.CodeRowMapper;
import com.hcemanager.dao.entities.interfaces.PersonDAO;
import com.hcemanager.dao.entities.rowMappers.PersonRowMapper;
import com.hcemanager.dao.springServices.SpringServices;
import com.hcemanager.exceptions.codes.CodeExistsException;
import com.hcemanager.exceptions.codes.CodeNotExistsException;
import com.hcemanager.exceptions.entities.*;
import com.hcemanager.models.codes.Code;
import com.hcemanager.models.entities.LivingSubject;
import com.hcemanager.models.entities.Person;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.util.List;

/**
 * @author Daniel Bellon & Juan Diaz
 */
public class PersonDAOImpl extends JdbcDaoSupport implements PersonDAO {

    //------------------------------------------------------------------------------------------------------------------
    //SQL sentence constants
    //------------------------------------------------------------------------------------------------------------------

    public static final String INSERT = "insert into Person (idPerson, addr, LivingSubject_idLivingSubject) values (?,?,?)";
    public static final String UPDATE = "update Person set addr=? where idPerson=?";
    public static final String DELETE = "delete from Person where idPerson=?";
    public static final String SELECT_PERSON = "select * from Person inner join LivingSubject inner join Entity " +
            "where LivingSubject_idLivingSubject=idLivingSubject and Entity_idEntity=idEntity and idPerson=?";
    public static final String SELECT_PERSONS = "select * from Person inner join LivingSubject inner join Entity " +
            "where LivingSubject_idLivingSubject=idLivingSubject and Entity_idEntity=idEntity";
    public static final String INSERT_CODE = "insert into Person_has_Codes (Codes_idCodes,Person_idPerson,type) values (?,?,?);";
    public static final String UPDATE_CODE = "update Person_has_Codes set Codes_idCodes = ? where Person_idPerson=? and type=?;";
    public static final String DELETE_CODE = "delete from Person_has_Codes where Person_idPerson=?;";
    public static final String SELECT_CODES = "select * from Person_has_Codes inner join Codes where Codes_idCodes=idCodes " +
            "and Person_idPerson=?";


    //------------------------------------------------------------------------------------------------------------------
    //CRUD methods
    //------------------------------------------------------------------------------------------------------------------


    /**
     * Insert an Person.
     * @param person
     * @throws PersonExistException
     */
    @Override
    public void insertPerson(Person person) throws PersonExistException, LivingSubjectExistsException, EntityExistsException {
        try {

            SpringServices.getLivingSubjectDAO().insertLivingSubject(person);

            getJdbcTemplate().update(INSERT,
                    person.getIdPerson(),
                    person.getAddr(),
                    person.getIdLivingSubject());
            
            //Code
            
            getJdbcTemplate().update(INSERT_CODE,
                    person.getMaritalStatusCode().getId(),
                    person.getIdPerson(),
                    person.getMaritalStatusCode().getType());

            getJdbcTemplate().update(INSERT_CODE,
                    person.getEducationLevelCode().getId(),
                    person.getIdPerson(),
                    person.getEducationLevelCode().getType());

            getJdbcTemplate().update(INSERT_CODE,
                    person.getRaceCode().getId(),
                    person.getIdPerson(),
                    person.getRaceCode().getType());

            getJdbcTemplate().update(INSERT_CODE,
                    person.getDisabilityCode().getId(),
                    person.getIdPerson(),
                    person.getDisabilityCode().getType());

            getJdbcTemplate().update(INSERT_CODE,
                    person.getLivingArrangementCode().getId(),
                    person.getIdPerson(),
                    person.getLivingArrangementCode().getType());

            getJdbcTemplate().update(INSERT_CODE,
                    person.getReligiousAffiliationCode().getId(),
                    person.getIdPerson(),
                    person.getReligiousAffiliationCode().getType());

            getJdbcTemplate().update(INSERT_CODE,
                    person.getEthnicGroupCode().getId(),
                    person.getIdPerson(),
                    person.getEthnicGroupCode().getType());



        }catch (DataIntegrityViolationException e){
            throw new PersonExistException(e);
        }
    }

    /**
     * Update an Person.
     * @param person
     * @throws com.hcemanager.exceptions.entities.PersonNotExistException
     * @throws com.hcemanager.exceptions.entities.PersonExistException
     */
    @Override
    public void updatePerson(Person person) throws PersonNotExistException, PersonExistException, LivingSubjectExistsException, EntityExistsException, LivingSubjectNotExistsException, EntityNotExistsException, CodeNotExistsException, CodeExistsException {

        if (isDifferent(person)){

            try {

                SpringServices.getLivingSubjectDAO().updateLivingSubject(person);

                int rows = getJdbcTemplate().update(UPDATE,
                        person.getAddr(),
                        person.getIdPerson());
                if(rows==0){
                    throw new PersonNotExistException();
                }
                updateCodes(person);
            }catch (DataIntegrityViolationException e){
                throw new PersonExistException(e);
            }
        }else{
            SpringServices.getLivingSubjectDAO().updateLivingSubject(person);
            updateCodes(person);
        }

    }

    /**
     * Delete an Person.
     * @param id
     * @throws PersonNotExistException
     * @throws PersonCannotBeDeletedException
     */
    @Override
    public void deletePerson(String id) throws PersonNotExistException, PersonCannotBeDeletedException, LivingSubjectCannotBeDeletedException, EntityCannotBeDeletedException, LivingSubjectNotExistsException, EntityNotExistsException, CodeNotExistsException {
        try {
            LivingSubject livingSubject = getPerson(id);

            getJdbcTemplate().update(DELETE_CODE,id);

            int rows = getJdbcTemplate().update(DELETE, id);
            if (rows==0){
                throw new PersonNotExistException();
            }

            SpringServices.getLivingSubjectDAO().deleteLivingSubject(livingSubject.getIdLivingSubject());

        }catch (DataIntegrityViolationException e){
            throw new PersonCannotBeDeletedException(e);
        }
    }

    /**
     * Get an Person
     * @param id
     * @return
     * @throws PersonNotExistException
     */
    @Override
    public Person getPerson(String id) throws PersonNotExistException, CodeNotExistsException {
        try {
            Person person = getJdbcTemplate().queryForObject(SELECT_PERSON, new PersonRowMapper(), id);

            setPersonCodes(person, getPersonCodes(id));

            return person;
        }catch (EmptyResultDataAccessException e){
            throw new PersonNotExistException(e);
        }
    }

    /**
     * Get all Person
     * @return
     * @throws PersonNotExistException
     */
    @Override
    public List<Person> getPersons() throws PersonNotExistException, CodeNotExistsException {
        try {
            List<Person> persons = getJdbcTemplate().query(SELECT_PERSONS, new PersonRowMapper());

            for (Person person:persons){
                setPersonCodes(person, getPersonCodes(person.getIdPerson()));
                persons.set(persons.indexOf(person),person);
            }

            return persons;
        }catch (EmptyResultDataAccessException e){
            throw new PersonNotExistException(e);
        }
    }

    /**
     * Get Codes of an Person by id.
     * @param id
     * @return
     * @throws CodeNotExistsException
     */
    public List<Code> getPersonCodes(String id) throws CodeNotExistsException {
        try{
            List<Code> codes = getJdbcTemplate().query(SELECT_CODES, new CodeRowMapper(), id);
            return codes;
        }catch (EmptyResultDataAccessException e){
            throw new CodeNotExistsException(e);
        }
    }

    /**
     * Set Codes to Person.
     * @param person
     * @param codes
     * @return
     */
    public void setPersonCodes(Person person, List<Code> codes) throws CodeNotExistsException {

        SpringServices.getLivingSubjectDAO().setLivingSubjectCodes(person,
                SpringServices.getLivingSubjectDAO().getLivingSubjectCodes(person.getIdLivingSubject()));

        for (Code code:codes){

            String typeCode = code.getType();

            if (typeCode.equals("maritalStatusCode")){
                person.setMaritalStatusCode(code);
            }else if (typeCode.equals("educationLevelCode")){
                person.setEducationLevelCode(code);
            }else if (typeCode.equals("raceCode")){
                person.setRaceCode(code);
            }else if (typeCode.equals("disabilityCode")){
                person.setDisabilityCode(code);
            }else if (typeCode.equals("livingArrangementCode")){
                person.setLivingArrangementCode(code);
            }else if (typeCode.equals("religiousAffiliationCode")){
                person.setReligiousAffiliationCode(code);
            }else if (typeCode.equals("ethnicGroupCode")){
                person.setEthnicGroupCode(code);
            }
        }
    }

    private boolean isDifferent(Person person) throws PersonNotExistException, CodeNotExistsException {
        Person oldPerson = getPerson(person.getIdPerson());

        int attrDifferent = 0;

        if(!person.getAddr().equals(oldPerson.getAddr())){
            attrDifferent++;
        }

        return attrDifferent!=0;
    }

    private void updateCodes(Person person) throws CodeNotExistsException, CodeExistsException {
        List<Code> oldCodes = getPersonCodes(person.getIdPerson());

        for (Code code:oldCodes){
            try {
                if (code.getType().equalsIgnoreCase(person.getMaritalStatusCode().getType())){
                    if (!code.getId().equals(person.getMaritalStatusCode().getId())){
                        getJdbcTemplate().update(UPDATE_CODE,
                                person.getMaritalStatusCode().getId(),
                                person.getIdPerson(),
                                person.getMaritalStatusCode().getType());
                    }
                }else if (code.getType().equalsIgnoreCase(person.getEducationLevelCode().getType())){
                    if (!code.getId().equals(person.getEducationLevelCode().getId())){
                        getJdbcTemplate().update(UPDATE_CODE,
                                person.getEducationLevelCode().getId(),
                                person.getIdPerson(),
                                person.getEducationLevelCode().getType());
                    }
                }else if (code.getType().equalsIgnoreCase(person.getRaceCode().getType())){
                    if (!code.getId().equals(person.getRaceCode().getId())){
                        getJdbcTemplate().update(UPDATE_CODE,
                                person.getRaceCode().getId(),
                                person.getIdPerson(),
                                person.getRaceCode().getType());
                    }
                }else if (code.getType().equalsIgnoreCase(person.getDisabilityCode().getType())){
                    if (!code.getId().equals(person.getDisabilityCode().getId())){
                        getJdbcTemplate().update(UPDATE_CODE,
                                person.getDisabilityCode().getId(),
                                person.getIdPerson(),
                                person.getDisabilityCode().getType());
                    }
                }else if (code.getType().equalsIgnoreCase(person.getLivingArrangementCode().getType())){
                    if (!code.getId().equals(person.getLivingArrangementCode().getId())){
                        getJdbcTemplate().update(UPDATE_CODE,
                                person.getLivingArrangementCode().getId(),
                                person.getIdPerson(),
                                person.getLivingArrangementCode().getType());
                    }
                }else if (code.getType().equalsIgnoreCase(person.getReligiousAffiliationCode().getType())){
                    if (!code.getId().equals(person.getReligiousAffiliationCode().getId())){
                        getJdbcTemplate().update(UPDATE_CODE,
                                person.getReligiousAffiliationCode().getId(),
                                person.getIdPerson(),
                                person.getReligiousAffiliationCode().getType());
                    }
                }else if (code.getType().equalsIgnoreCase(person.getEthnicGroupCode().getType())){
                    if (!code.getId().equals(person.getEthnicGroupCode().getId())){
                        getJdbcTemplate().update(UPDATE_CODE,
                                person.getEthnicGroupCode().getId(),
                                person.getIdPerson(),
                                person.getEthnicGroupCode().getType());
                    }
                }
            }catch (DataIntegrityViolationException e){
                throw new CodeExistsException(e);
            }

        }
    }
}
