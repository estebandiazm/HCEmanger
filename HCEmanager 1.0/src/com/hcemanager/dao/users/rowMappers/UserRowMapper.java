package com.hcemanager.dao.users.rowMappers;

import com.hcemanager.dao.entities.rowMappers.PersonRowMapper;
import com.hcemanager.exceptions.utils.CannotDecryptException;
import com.hcemanager.models.entities.Person;
import com.hcemanager.models.users.User;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import java.security.InvalidKeyException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Daniel Bellon & Juan Diaz
 */
public class UserRowMapper implements ParameterizedRowMapper<User> {

    PersonRowMapper personRowMapper = new PersonRowMapper();

    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {

        Person person = personRowMapper.mapRow(resultSet,i);
        User user = new User();

        //--------------------------------------------------------------------------------------------------------------
        //User Arguments
        //--------------------------------------------------------------------------------------------------------------
        user.setIdUser(resultSet.getString("idUser"));
        user.setTypeUser(resultSet.getString("typeUser"));
        try {
            user.setPassword(user.decryptPassword(resultSet.getString("password")));
        } catch (CannotDecryptException e) {
            e.printStackTrace();
        }
        //--------------------------------------------------------------------------------------------------------------
        //Person arguments
        //--------------------------------------------------------------------------------------------------------------
        user.setIdPerson(person.getIdPerson());
        user.setAddr(person.getAddr());
        //--------------------------------------------------------------------------------------------------------------
        //LivingSubject arguments
        //--------------------------------------------------------------------------------------------------------------
        user.setIdLivingSubject(person.getIdLivingSubject());
        user.setBirthTime(person.getBirthTime());
        user.setDeceasedInd(person.isDeceasedInd());
        user.setDeceasedTime(person.getDeceasedTime());
        user.setMultipleBirthInd(person.isMultipleBirthInd());
        user.setMultipleBirthOrderNumber(person.getMultipleBirthOrderNumber());
        user.setOrganDonorInd(person.isOrganDonorInd());
        //--------------------------------------------------------------------------------------------------------------
        //Entity arguments
        //--------------------------------------------------------------------------------------------------------------
        user.setId(person.getId());
        user.setQuantity(person.getQuantity());
        user.setName(person.getName());
        user.setDescription(person.getDescription());
        user.setExistenceTime(person.getExistenceTime());
        user.setTelecom(person.getTelecom());

        return user;
    }
}
