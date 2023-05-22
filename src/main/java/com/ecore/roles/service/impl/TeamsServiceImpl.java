package com.ecore.roles.service.impl;

import com.ecore.roles.client.TeamsClient;
import com.ecore.roles.client.model.Team;
import com.ecore.roles.exception.ResourceNotFoundException;
import com.ecore.roles.service.TeamsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TeamsServiceImpl implements TeamsService {

    private final TeamsClient teamsClient;

    @Autowired
    public TeamsServiceImpl(TeamsClient teamsClient) {
        this.teamsClient = teamsClient;
    }

    public Team getTeam(UUID id) {
        ResponseEntity<Team> team = teamsClient.getTeam(id);

        if((team.getStatusCode() != HttpStatus.OK) || (team.getBody() == null)) {
            throw new ResourceNotFoundException(Team.class, id);
        }

        return team.getBody();
    }

    public List<Team> getTeams() {
        return teamsClient.getTeams().getBody();
    }
}
