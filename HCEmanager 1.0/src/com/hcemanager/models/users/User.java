package com.hcemanager.models.users;

import com.hcemanager.exceptions.utils.CannotDecryptException;
import com.hcemanager.models.entities.Person;
import com.hcemanager.utils.Segurity;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.*;
import javax.validation.constraints.Size;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

/**
 * @author Daniel Bellon & Juan Diaz
 */
public class User extends Person{

    public static final String SEGURITY_KEY = "HCEseguritykey";
    private String idUser;
    private String typeUser;
    private String password;
    private KeyGenerator generatorDES;


    public String getIdUser() {return idUser;}
    public void setIdUser(String idUser) {this.idUser = idUser;}
    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}
    public String encryptPassword(String password) {
        return Segurity.encrypt(password, SEGURITY_KEY);
    }
    public String decryptPassword(String encryptedPassword) throws CannotDecryptException {
        return Segurity.decrypt(encryptedPassword,SEGURITY_KEY);
    }

    public String getTypeUser() {
        return typeUser;
    }

    public void setTypeUser(String typeUser) {
        this.typeUser = typeUser;
    }
}
