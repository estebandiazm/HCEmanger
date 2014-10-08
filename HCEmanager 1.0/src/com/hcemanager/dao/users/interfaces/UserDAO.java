package com.hcemanager.dao.users.interfaces;

import com.hcemanager.exceptions.codes.CodeExistsException;
import com.hcemanager.exceptions.codes.CodeNotExistsException;
import com.hcemanager.exceptions.entities.*;
import com.hcemanager.exceptions.users.UserCannotBeDeletedException;
import com.hcemanager.exceptions.users.UserExistsException;
import com.hcemanager.exceptions.users.UserNotExistsException;
import com.hcemanager.models.users.User;

import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * @author Daniel Bellon & Juan Diaz
 */
public interface UserDAO {

    public void insertUser(User user) throws UserExistsException, EntityExistsException, PersonExistException,
            LivingSubjectExistsException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException;

    public void updateUser(User user) throws UserExistsException, EntityExistsException, LivingSubjectExistsException,
            CodeExistsException, LivingSubjectNotExistsException, PersonExistException, CodeNotExistsException,
            PersonNotExistException, EntityNotExistsException, UserNotExistsException, NoSuchPaddingException,
            NoSuchAlgorithmException, InvalidKeyException;

    public void deleteUser(String id) throws UserCannotBeDeletedException, UserNotExistsException,
            LivingSubjectCannotBeDeletedException, EntityCannotBeDeletedException, LivingSubjectNotExistsException,
            EntityNotExistsException, CodeNotExistsException, PersonNotExistException, PersonCannotBeDeletedException;

    public User getUser(String id) throws UserNotExistsException, CodeNotExistsException;

    public List<User> getUsers() throws UserNotExistsException, CodeNotExistsException;

    public User getUserByIdPerson(String id) throws CodeNotExistsException, UserNotExistsException;
}
