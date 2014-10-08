package com.hcemanager.dao.users.implementations;

import com.hcemanager.dao.springServices.SpringServices;
import com.hcemanager.dao.users.interfaces.UserDAO;
import com.hcemanager.dao.users.rowMappers.UserRowMapper;
import com.hcemanager.exceptions.codes.CodeExistsException;
import com.hcemanager.exceptions.codes.CodeNotExistsException;
import com.hcemanager.exceptions.entities.*;
import com.hcemanager.exceptions.users.UserCannotBeDeletedException;
import com.hcemanager.exceptions.users.UserExistsException;
import com.hcemanager.exceptions.users.UserNotExistsException;
import com.hcemanager.models.entities.Person;
import com.hcemanager.models.users.User;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * @author Daniel Bellon & Juan Diaz
 */
public class UserDAOImpl extends JdbcDaoSupport implements UserDAO{

    //------------------------------------------------------------------------------------------------------------------
    //SQL sentences
    //------------------------------------------------------------------------------------------------------------------
    public static final String INSERT = "insert into User(idUser,typeUser,password,Person_idPerson) values (?,?,?,?);";
    public static final String UPDATE = "update User set password = ?, typeUser=? where idUser=?;";
    public static final String DELETE = "delete from User where idUser=?;";
    public static final String SELECT_USER = "select * from User inner join Person inner join LivingSubject " +
            "inner join Entity where Person_idPerson=idPerson and LivingSubject_idLivingSubject=idLivingSubject " +
            "and Entity_idEntity=idEntity and idUser=?;";
    public static final String SELECT_USERS = "select * from User inner join Person inner join LivingSubject " +
            "inner join Entity where Person_idPerson=idPerson and LivingSubject_idLivingSubject=idLivingSubject " +
            "and Entity_idEntity=idEntity;";
    public static final String SELECT_USER_BY_ID_PERSON = "select * from User inner join Person inner join LivingSubject " +
            "inner join Entity where Person_idPerson=idPerson and LivingSubject_idLivingSubject=idLivingSubject " +
            "and Entity_idEntity=idEntity and idPerson=?;";

    //------------------------------------------------------------------------------------------------------------------
    //Methods
    //------------------------------------------------------------------------------------------------------------------

    @Override
    public void insertUser(User user) throws UserExistsException, EntityExistsException, PersonExistException,
            LivingSubjectExistsException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {

        try {

            SpringServices.getPersonDAO().insertPerson(user);

            getJdbcTemplate().update(INSERT,
                    user.getIdUser(),
                    user.getTypeUser(),
                    user.encryptPassword(user.getPassword()),
                    user.getIdPerson());

        }catch (DataIntegrityViolationException e){
            throw new UserExistsException(e);
        }
    }

    @Override
    public void updateUser(User user) throws UserExistsException, EntityExistsException, LivingSubjectExistsException, CodeExistsException, LivingSubjectNotExistsException, PersonExistException, CodeNotExistsException, PersonNotExistException, EntityNotExistsException, UserNotExistsException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {

        if (isDifferent(user)){
            try {
                SpringServices.getPersonDAO().updatePerson(user);

                int rows = getJdbcTemplate().update(UPDATE,
                        user.encryptPassword(user.getPassword()),
                        user.getTypeUser(),
                        user.getIdUser());

                if (rows==0){
                    throw new UserNotExistsException();
                }
            }catch (DataIntegrityViolationException e){
                throw new UserExistsException(e);
            }
        }else{
            SpringServices.getPersonDAO().updatePerson(user);
        }
    }


    @Override
    public void deleteUser(String id) throws UserCannotBeDeletedException, UserNotExistsException, LivingSubjectCannotBeDeletedException, EntityCannotBeDeletedException, LivingSubjectNotExistsException, EntityNotExistsException, CodeNotExistsException, PersonNotExistException, PersonCannotBeDeletedException {
        try {

            Person person = getUser(id);

            int rows = getJdbcTemplate().update(DELETE, id);
            if (rows==0){
                throw new UserNotExistsException();
            }
            SpringServices.getPersonDAO().deletePerson(person.getIdPerson());
        }catch (DataIntegrityViolationException e){
            throw new UserCannotBeDeletedException(e);
        }
    }

    @Override
    public User getUser(String id) throws UserNotExistsException, CodeNotExistsException {


        try {
            User user = getJdbcTemplate().queryForObject(SELECT_USER,new UserRowMapper(), id);
            SpringServices.getPersonDAO().setPersonCodes(user,
                    SpringServices.getPersonDAO().getPersonCodes(user.getIdPerson()));
            return user;
        }catch (EmptyResultDataAccessException e){
            throw new UserNotExistsException(e);
        }


    }

    @Override
    public User getUserByIdPerson(String id) throws CodeNotExistsException, UserNotExistsException {
        try {
            User user = getJdbcTemplate().queryForObject(SELECT_USER_BY_ID_PERSON, new UserRowMapper(), id);
                    SpringServices.getPersonDAO().setPersonCodes(user,
                    SpringServices.getPersonDAO().getPersonCodes(user.getIdPerson()));
            return user;
        }catch (EmptyResultDataAccessException e){
            throw new UserNotExistsException(e);
        }
    }

    @Override
    public List<User> getUsers() throws UserNotExistsException, CodeNotExistsException {
        try {
            List<User> users = getJdbcTemplate().query(SELECT_USERS, new UserRowMapper());
            for (User user:users){
                SpringServices.getPersonDAO().setPersonCodes(user,
                        SpringServices.getPersonDAO().getPersonCodes(user.getIdPerson()));
                users.set(users.indexOf(user),user);
            }

            return users;
        }catch (EmptyResultDataAccessException e){
            throw new UserNotExistsException(e);
        }
    }

    private boolean isDifferent(User user) throws UserNotExistsException, CodeNotExistsException {
        User oldUser = getUser(user.getIdUser());

        int atrDifferent = 0;

        if (!user.getPassword().equals(oldUser.getPassword())){
            atrDifferent++;
        }
         return atrDifferent!=0;
    }
}
