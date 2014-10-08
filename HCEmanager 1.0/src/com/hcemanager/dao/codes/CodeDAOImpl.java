package com.hcemanager.dao.codes;

import com.hcemanager.exceptions.codes.CodeCannotBeDeletedException;
import com.hcemanager.exceptions.codes.CodeExistsException;
import com.hcemanager.exceptions.codes.CodeNotExistsException;
import com.hcemanager.models.codes.Code;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.util.List;

/**
 * @author daniel, juan.
 */
public class CodeDAOImpl extends JdbcDaoSupport implements CodeDAO {
    public static final String INSERT = "insert into Codes(idCodes, mnemonic, name, description) values (?,?,?,?);";
    public static final String UPDATE = "update Codes set mnemonic=?, name=?, description=? where idCodes=?;";
    public static final String DELETE = "delete from Codes where idCodes=?";
    public static final String SELECT_CODE = "select * from Codes where idCodes=?";
    public static final String SELECT_CODES = "select * from Codes";

    //------------------------------------------------------------------------------------------------------------------
    //CRUD methods
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Insert Code
     * @param code
     * @throws CodeExistsException
     */
    @Override
    public void insertCode(Code code) throws CodeExistsException {
        try{
            getJdbcTemplate().update(INSERT,
                    code.getId(),
                    code.getMnemonic(),
                    code.getPrintName(),
                    code.getDescription());
        }catch (DataIntegrityViolationException e){
            throw new CodeExistsException(e);
        }
    }

    /**
     * Update Code
     * @param code
     * @throws CodeExistsException
     * @throws CodeNotExistsException
     */
    @Override
    public void updateCode(Code code) throws CodeExistsException, CodeNotExistsException {
        try{
            int rows = getJdbcTemplate().update(UPDATE,
                    code.getMnemonic(),
                    code.getPrintName(),
                    code.getDescription(),
                    code.getId());

            if (rows==0){
                throw new CodeNotExistsException();
            }

        }catch (DataIntegrityViolationException e){
            throw new CodeExistsException(e);
        }

    }

    /**
     * Delete Code
     * @param id
     * @throws CodeNotExistsException
     * @throws CodeCannotBeDeletedException
     */
    @Override
    public void deleteCode(String id) throws CodeNotExistsException, CodeCannotBeDeletedException {
        try{
            int rows = getJdbcTemplate().update(DELETE, id);

            if (rows==0){
                throw new CodeNotExistsException();
            }
        }catch (DataIntegrityViolationException e){
            throw new CodeCannotBeDeletedException(e);
        }
    }

    /**
     * Get an Code
     * @param id
     * @return Code
     * @throws CodeNotExistsException
     */
    @Override
    public Code getCode(String id) throws CodeNotExistsException {
        try {
            Code code = getJdbcTemplate().queryForObject(SELECT_CODE, new CodeRowMapper(), id);
            return code;
        }catch (EmptyResultDataAccessException e){
            throw new CodeNotExistsException(e);
        }
    }

    /**
     * Get all Codes
     * @return List<Code>
     * @throws CodeNotExistsException
     */
    @Override
    public List<Code> getCodes() throws CodeNotExistsException {
        try {
            List<Code> codes = getJdbcTemplate().query(SELECT_CODES, new CodeRowMapper());
            return codes;
        }catch (EmptyResultDataAccessException e){
            throw new CodeNotExistsException(e);
        }
    }
}
