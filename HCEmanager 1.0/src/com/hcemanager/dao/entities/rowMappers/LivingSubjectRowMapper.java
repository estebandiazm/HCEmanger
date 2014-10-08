package com.hcemanager.dao.entities.rowMappers;

import com.hcemanager.models.entities.Entity;
import com.hcemanager.models.entities.LivingSubject;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author daniel, juan.
 */
public class LivingSubjectRowMapper implements ParameterizedRowMapper<LivingSubject>{

    EntityRowMapper entityRowMapper = new EntityRowMapper();

    /**
     * This class make a LivingSubject mapper.
     * @param resultSet
     * @param i
     * @return
     * @throws SQLException
     */
    @Override
    public LivingSubject mapRow(ResultSet resultSet, int i) throws SQLException {

        Entity entity = entityRowMapper.mapRow(resultSet, i);
        LivingSubject livingSubject = new LivingSubject();

        //--------------------------------------------------------------------------------------------------------------
        //LivingSubject arguments
        //--------------------------------------------------------------------------------------------------------------
        livingSubject.setIdLivingSubject(resultSet.getString("idLivingSubject"));
        livingSubject.setBirthTime(resultSet.getString("birthTime"));
        livingSubject.setDeceasedInd(resultSet.getBoolean("deceasedInd"));
        livingSubject.setDeceasedTime(resultSet.getString("deceasedTime"));
        livingSubject.setMultipleBirthInd(resultSet.getBoolean("multipleBirthInd"));
        livingSubject.setMultipleBirthOrderNumber(resultSet.getInt("multipleBirthOrderNumber"));
        livingSubject.setOrganDonorInd(resultSet.getBoolean("organDonorInd"));

        //--------------------------------------------------------------------------------------------------------------
        //Entity arguments
        //--------------------------------------------------------------------------------------------------------------
        livingSubject.setId(entity.getId());
        livingSubject.setQuantity(entity.getQuantity());
        livingSubject.setName(entity.getName());
        livingSubject.setDescription(entity.getDescription());
        livingSubject.setExistenceTime(entity.getExistenceTime());
        livingSubject.setTelecom(entity.getTelecom());

        //TODO: pruebas.
        return livingSubject;
    }
}
