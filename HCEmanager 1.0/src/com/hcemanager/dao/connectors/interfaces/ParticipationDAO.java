package com.hcemanager.dao.connectors.interfaces;

import com.hcemanager.exceptions.connectors.ParticipationCannotBeDeletedException;
import com.hcemanager.exceptions.connectors.ParticipationExistsException;
import com.hcemanager.exceptions.connectors.ParticipationNotExistsException;
import com.hcemanager.models.connectors.Participation;

import java.util.List;

/**
 * Created by juan on 10/04/14.
 */
public interface ParticipationDAO  {

    public void insertParticipation(Participation participation) throws ParticipationExistsException;
    public void updateParticipation(Participation participation) throws ParticipationNotExistsException, ParticipationExistsException;
    public void deleteParticipation(String id) throws ParticipationNotExistsException, ParticipationCannotBeDeletedException;
    public Participation getParticipation(String id) throws ParticipationNotExistsException;//TODO: revisar llave foranea de participation
    public List<Participation> getParticipations() throws ParticipationNotExistsException;
}
