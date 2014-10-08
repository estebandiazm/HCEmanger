package com.hcemanager.dao.codes;

import com.hcemanager.exceptions.codes.CodeCannotBeDeletedException;
import com.hcemanager.exceptions.codes.CodeExistsException;
import com.hcemanager.exceptions.codes.CodeNotExistsException;
import com.hcemanager.models.codes.Code;

import java.util.List;

/**
 * @author daniel.
 */
public interface CodeDAO {
    //todo: definir interface, implentar interfaz y definir rowmapper

    public void insertCode(Code code) throws CodeExistsException;
    public void updateCode(Code code) throws CodeExistsException, CodeNotExistsException;
    public void deleteCode(String id) throws CodeNotExistsException, CodeCannotBeDeletedException;
    public Code getCode(String id) throws CodeNotExistsException;
    public List<Code> getCodes() throws CodeNotExistsException;
}
