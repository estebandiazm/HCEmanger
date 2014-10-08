package com.hcemanager.utils;

import com.hcemanager.exceptions.utils.CannotDecryptException;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
/**
 * @author Daniel Bellon & Juan Diaz.
 */
public class Segurity {

    public Segurity() {
    }

    public static String encrypt(String message, String key){
        StandardPBEStringEncryptor stringEncryptor = new StandardPBEStringEncryptor();
        stringEncryptor.setPassword(key);
        return stringEncryptor.encrypt(message);
    }

    public static String decrypt (String message, String key) throws CannotDecryptException {
        StandardPBEStringEncryptor stringEncryptor = new StandardPBEStringEncryptor();
        stringEncryptor.setPassword(key);
        String messageDecrypt ="";
        try{
            messageDecrypt = stringEncryptor.decrypt(message);
        }catch (Exception e){
            throw new CannotDecryptException(e);
        }
        return messageDecrypt;
    }
}
