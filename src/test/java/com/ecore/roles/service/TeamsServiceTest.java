package com.ecore.roles.service;

import com.ecore.roles.client.TeamsClient;
import com.ecore.roles.client.model.Team;
import com.ecore.roles.client.model.User;
import com.ecore.roles.exception.ResourceNotFoundException;
import com.ecore.roles.service.impl.TeamsServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.ecore.roles.utils.TestData.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TeamsServiceTest {

    @InjectMocks
    private TeamsServiceImpl TeamsService;
    @Mock
    private TeamsClient TeamsClient;

    @Test
    void shouldGetTeamWhenTeamIdExists() {
        Team ordinaryCoralLynxTeam = ORDINARY_CORAL_LYNX_TEAM();
        when(TeamsClient.getTeam(ORDINARY_CORAL_LYNX_TEAM_UUID))
                .thenReturn(ResponseEntity
                        .status(HttpStatus.OK)
                        .body(ordinaryCoralLynxTeam));
        assertNotNull(TeamsService.getTeam(ORDINARY_CORAL_LYNX_TEAM_UUID));
    }

    @Test
    void shouldThrowExceptionIfTeamIdDoesNotExist() {
        UUID teamId = UUID.randomUUID();

        when(TeamsClient.getTeam(teamId))
                .thenReturn(ResponseEntity
                        .status(HttpStatus.OK)
                        .body(null));

        assertThrows(ResourceNotFoundException.class, () -> {
            TeamsService.getTeam(teamId);
        });
    }

    @Test
    void shouldReturnAListOfTeams() {
        List<Team> teamList = new ArrayList<>();

        teamList.add(ORDINARY_CORAL_LYNX_TEAM());

        when(TeamsClient.getTeams())
                .thenReturn(ResponseEntity
                        .status(HttpStatus.OK)
                        .body(teamList));

        assertEquals(1, TeamsService.getTeams().size());
    }
}
