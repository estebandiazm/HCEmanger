package com.hcemanager.dao.roles.rowMappers;

import com.hcemanager.models.roles.LicensedEntity;
import com.hcemanager.models.roles.Role;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author daniel, juan.
 */
public class LicensedEntityRowMapper implements ParameterizedRowMapper<LicensedEntity> {

    RoleRowMapper roleRowMapper = new RoleRowMapper();
    /**
     * This method make a LicensedEntity mapper.
     * @param resultSet
     * @param i
     * @return LicensedEntity.
     * @throws java.sql.SQLException
     */
    @Override
    public LicensedEntity mapRow(ResultSet resultSet, int i) throws SQLException {

        Role role = roleRowMapper.mapRow(resultSet,i);
        LicensedEntity licensedEntity = new LicensedEntity();

        //--------------------------------------------------------------------------------------------------------------
        //LicensedEntity arguments
        //--------------------------------------------------------------------------------------------------------------
        licensedEntity.setIdLicensedEntity(resultSet.getString("idLicensedEntity"));
        licensedEntity.setRecertificationTime(resultSet.getString("recertificationTime"));

        //--------------------------------------------------------------------------------------------------------------
        //Role arguments
        //--------------------------------------------------------------------------------------------------------------
        licensedEntity.setId(role.getId());
        licensedEntity.setNegationInd(role.isNegationInd());
        licensedEntity.setName(role.getName());
        licensedEntity.setAddr(role.getAddr());
        licensedEntity.setTelecom(role.getTelecom());
        licensedEntity.setEffectiveTime(role.getEffectiveTime());
        licensedEntity.setCertificateText(role.getCertificateText());
        licensedEntity.setQuantity(role.getQuantity());
        licensedEntity.setPositionNumber(role.getPositionNumber());

        //TODO: Completar codes y foreignkey.

        return licensedEntity;
    }
}
