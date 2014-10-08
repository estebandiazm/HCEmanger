package com.hcemanager.dao.connectors.rowMappers;

import com.hcemanager.models.connectors.Participation;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by juan on 10/04/14.
 */
public class ParticipationRowMapper implements ParameterizedRowMapper<Participation> {

    @Override
    public Participation mapRow(ResultSet resultSet, int i) throws SQLException {

        Participation participation = new Participation();
        participation.setIdParticipation(resultSet.getString("idParticipation"));
        participation.setIdRole(resultSet.getString("Role_idRole"));
        participation.setIdAct(resultSet.getString("Act_idAct"));
        participation.setSequenceNumber(resultSet.getString("sequenceNumber"));
        participation.setNegationInd(resultSet.getBoolean("negationInd"));
        participation.setNoteText(resultSet.getString("noteText"));
        participation.setTime(resultSet.getString("time"));
        participation.setSignatureText(resultSet.getString("signatureText"));
        participation.setPerformInd(resultSet.getBoolean("performInd"));

        return participation;
    }
}
