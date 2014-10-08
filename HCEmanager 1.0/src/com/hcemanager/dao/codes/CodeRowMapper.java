package com.hcemanager.dao.codes;

import com.hcemanager.models.codes.Code;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author daniel, juan.
 */
public class CodeRowMapper implements ParameterizedRowMapper<Code> {
    @Override
    public Code mapRow(ResultSet resultSet, int i) throws SQLException {

        Code code = new Code();
        code.setId(resultSet.getString("idCodes"));
        code.setMnemonic(resultSet.getString("mnemonic"));
        code.setPrintName(resultSet.getString("name"));
        code.setDescription(resultSet.getString("description"));
        if (resultSet.getString("typeCode")!=null){
            code.setType(resultSet.getString("typeCode"));
        } else {
            code.setType(resultSet.getString("type"));
        }

        return code;
    }
}
