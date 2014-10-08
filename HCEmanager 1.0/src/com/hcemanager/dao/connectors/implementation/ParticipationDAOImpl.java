package com.hcemanager.dao.connectors.implementation;

import com.hcemanager.dao.connectors.rowMappers.ParticipationRowMapper;
import com.hcemanager.dao.connectors.interfaces.ParticipationDAO;
import com.hcemanager.exceptions.connectors.ParticipationExistsException;
import com.hcemanager.exceptions.connectors.ParticipationNotExistsException;
import com.hcemanager.exceptions.connectors.ParticipationCannotBeDeletedException;
import com.hcemanager.models.connectors.Participation;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.util.List;

/**
 * Created by juan on 10/04/14.
 */
public class ParticipationDAOImpl extends JdbcDaoSupport implements ParticipationDAO {


    public static final String INSERT = "insert into Participation(idParticipation,Role_idRole,Act_idAct,sequenceNumber,negationInd,noteText,time,signatureText,performInd) values (?,?,?,?,?,?,?,?,?);";
    public static final String UPDATE = "update Participation set Role_idRole=?, Act_idAct=?, sequenceNumber=?, negationInd=?, noteText=?, time=?, signatureText=?, performInd=? where idParticipation=?";
    public static final String DELETE = "delete from Participation where idParticipation=?";
    public static final String SELECT_PARTICIPATION = "select * from Participation where idParticipation=?";
    public static final String SELECT_PARTICIPATIONS = "select * from Participation";

    /**
     * Insert an Participation.
     * @param participation
     * @throws ParticipationExistsException
     */
    @Override
    public void insertParticipation(Participation participation) throws ParticipationExistsException {
           try{
               getJdbcTemplate().update(INSERT,
                       participation.getIdParticipation(),
                       participation.getIdRole(),
                       participation.getIdAct(),
                       participation.getSequenceNumber(),
                       participation.isNegationInd(),
                       participation.getNoteText(),
                       participation.getTime(),
                       participation.getSignatureText(),
                       participation.isPerformInd());
           }catch (DataIntegrityViolationException e){
               throw new ParticipationExistsException(e);
           }
    }

    /**
     * Update an Participation
     * @param participation
     * @throws ParticipationNotExistsException
     * @throws ParticipationExistsException
     */
    @Override
    public void updateParticipation(Participation participation) throws ParticipationNotExistsException, ParticipationExistsException {
        try {
            int rows = getJdbcTemplate().update(UPDATE,
                    participation.getIdRole(),
                    participation.getIdAct(),
                    participation.getSequenceNumber(),
                    participation.isNegationInd(),
                    participation.getNoteText(),
                    participation.getTime(),
                    participation.getSignatureText(),
                    participation.isPerformInd(),
                    participation.getIdParticipation());
            if (rows==0){
                throw new ParticipationNotExistsException();
            }
        }catch (DataIntegrityViolationException e){
            throw new ParticipationExistsException(e);
        }
    }

    /**
     * Delete an Participation
     * @param id
     * @throws ParticipationNotExistsException
     * @throws ParticipationCannotBeDeletedException
     */
    @Override
    public void deleteParticipation(String id) throws ParticipationNotExistsException, ParticipationCannotBeDeletedException {
        try{
            int rows = getJdbcTemplate().update(DELETE, id);
            if (rows==0){
                throw new ParticipationNotExistsException();
            }
        }catch (DataIntegrityViolationException e){
            throw new ParticipationCannotBeDeletedException(e);
        }
    }

    /**
     * Get an Participation by id.
     * @param id
     * @return
     */
    @Override
    public Participation getParticipation(String id) throws ParticipationNotExistsException {
        try {
            Participation participation = getJdbcTemplate().queryForObject(SELECT_PARTICIPATION,
                    new ParticipationRowMapper(), id);
            return participation;
        }catch (EmptyResultDataAccessException e){
            throw new ParticipationNotExistsException(e);
        }
    }

    @Override
    public List<Participation> getParticipations() throws ParticipationNotExistsException {
        try {
            List<Participation> participations = getJdbcTemplate().query(SELECT_PARTICIPATIONS, new ParticipationRowMapper());
            return participations;
        }catch (EmptyResultDataAccessException e){
            throw new ParticipationNotExistsException(e);
        }
    }
}
