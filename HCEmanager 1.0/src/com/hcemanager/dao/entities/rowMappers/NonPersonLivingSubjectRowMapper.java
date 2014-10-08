package com.hcemanager.dao.entities.rowMappers;

import com.hcemanager.models.entities.LivingSubject;
import com.hcemanager.models.entities.NonPersonLivingSubject;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author daniel, juan.
 */
public class NonPersonLivingSubjectRowMapper implements ParameterizedRowMapper<NonPersonLivingSubject> {

    LivingSubjectRowMapper livingSubjectRowMapper = new LivingSubjectRowMapper();

    /**
     * This class make a NonPersonLivingSubject mapper.
     * @param resultSet
     * @param i
     * @return
     * @throws java.sql.SQLException
     */
    @Override
    public NonPersonLivingSubject mapRow(ResultSet resultSet, int i) throws SQLException {

        LivingSubject  livingSubject = livingSubjectRowMapper.mapRow(resultSet,i);
        NonPersonLivingSubject nonPersonLivingSubject = new NonPersonLivingSubject();

        //--------------------------------------------------------------------------------------------------------------
        //LivingSubject arguments
        //--------------------------------------------------------------------------------------------------------------
        nonPersonLivingSubject.setIdNonPersonLivingSubject(resultSet.getString("IdNonPersonLivingSubject"));
        nonPersonLivingSubject.setStrainText(resultSet.getString("strainText"));

        //--------------------------------------------------------------------------------------------------------------
        //LivingSubject arguments
        //--------------------------------------------------------------------------------------------------------------
        nonPersonLivingSubject.setIdLivingSubject(livingSubject.getIdLivingSubject());
        nonPersonLivingSubject.setBirthTime(livingSubject.getBirthTime());
        nonPersonLivingSubject.setDeceasedInd(livingSubject.isDeceasedInd());
        nonPersonLivingSubject.setDeceasedTime(livingSubject.getDeceasedTime());
        nonPersonLivingSubject.setMultipleBirthInd(livingSubject.isMultipleBirthInd());
        nonPersonLivingSubject.setMultipleBirthOrderNumber(livingSubject.getMultipleBirthOrderNumber());
        nonPersonLivingSubject.setOrganDonorInd(livingSubject.isOrganDonorInd());

        //--------------------------------------------------------------------------------------------------------------
        //Entity arguments
        //--------------------------------------------------------------------------------------------------------------
        nonPersonLivingSubject.setId(livingSubject.getId());
        nonPersonLivingSubject.setQuantity(livingSubject.getQuantity());
        nonPersonLivingSubject.setName(livingSubject.getName());
        nonPersonLivingSubject.setDescription(livingSubject.getDescription());
        nonPersonLivingSubject.setExistenceTime(livingSubject.getExistenceTime());
        nonPersonLivingSubject.setTelecom(livingSubject.getTelecom());

        return nonPersonLivingSubject;
    }
}
