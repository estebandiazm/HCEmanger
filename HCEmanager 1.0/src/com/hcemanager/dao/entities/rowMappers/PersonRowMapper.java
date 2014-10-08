package com.hcemanager.dao.entities.rowMappers;

import com.hcemanager.models.entities.LivingSubject;
import com.hcemanager.models.entities.Person;
import com.sun.corba.se.impl.orb.PropertyOnlyDataCollector;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Daniel Bellon & Juan Diaz
 */
public class PersonRowMapper implements ParameterizedRowMapper<Person> {

    LivingSubjectRowMapper livingSubjectRowMapper= new LivingSubjectRowMapper();

    /**
     * This class make a Person mapper.
     * @param resultSet
     * @param i
     * @return
     * @throws java.sql.SQLException
     */
    @Override
    public Person mapRow(ResultSet resultSet, int i) throws SQLException {

        LivingSubject livingSubject = livingSubjectRowMapper.mapRow(resultSet, i);
        Person person = new Person();

        //--------------------------------------------------------------------------------------------------------------
        //Person arguments
        //--------------------------------------------------------------------------------------------------------------
        person.setIdPerson(resultSet.getString("idPerson"));
        person.setAddr(resultSet.getString("addr"));


        //--------------------------------------------------------------------------------------------------------------
        //LivingSubject arguments
        //--------------------------------------------------------------------------------------------------------------
        person.setIdLivingSubject(livingSubject.getIdLivingSubject());
        person.setBirthTime(livingSubject.getBirthTime());
        person.setDeceasedInd(livingSubject.isDeceasedInd());
        person.setDeceasedTime(livingSubject.getDeceasedTime());
        person.setMultipleBirthInd(livingSubject.isMultipleBirthInd());
        person.setMultipleBirthOrderNumber(livingSubject.getMultipleBirthOrderNumber());
        person.setOrganDonorInd(livingSubject.isOrganDonorInd());

        //--------------------------------------------------------------------------------------------------------------
        //Entity arguments
        //--------------------------------------------------------------------------------------------------------------
        person.setId(livingSubject.getId());
        person.setQuantity(livingSubject.getQuantity());
        person.setName(livingSubject.getName());
        person.setDescription(livingSubject.getDescription());
        person.setExistenceTime(livingSubject.getExistenceTime());
        person.setTelecom(livingSubject.getTelecom());



        return person;
    }
}
