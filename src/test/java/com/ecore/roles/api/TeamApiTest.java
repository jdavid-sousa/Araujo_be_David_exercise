package com.ecore.roles.api;

import com.ecore.roles.client.TeamsClient;
import com.ecore.roles.client.UsersClient;
import com.ecore.roles.client.model.Team;
import com.ecore.roles.client.model.User;
import com.ecore.roles.exception.ResourceNotFoundException;
import com.ecore.roles.service.TeamsService;
import com.ecore.roles.service.impl.TeamsServiceImpl;
import com.ecore.roles.service.impl.UsersServiceImpl;
import com.ecore.roles.utils.RestAssuredHelper;
import com.ecore.roles.web.dto.TeamDto;
import com.ecore.roles.web.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.ecore.roles.utils.MockUtils.*;
import static com.ecore.roles.utils.RestAssuredHelper.*;
import static com.ecore.roles.utils.TestData.*;
import static com.ecore.roles.utils.TestData.UUID_1;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TeamApiTest {
    private final RestTemplate restTemplate;

    @InjectMocks
    private TeamsServiceImpl usersService;

    @Mock
    private TeamsClient teamsClient;

    private MockRestServiceServer mockServer;

    @LocalServerPort
    private int port;

    @Autowired
    public TeamApiTest(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @BeforeEach
    void setUp() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
        RestAssuredHelper.setUp(port);
    }

    @Test
    void shouldGetTeams() {
        List<Team> teamList = new ArrayList<>();
        teamList.add(ORDINARY_CORAL_LYNX_TEAM());

        mockGetTeams(mockServer, teamList);

        TeamDto[] users = getTeams()
                .extract().as(TeamDto[].class);

        assertThat(teamList).isNotEmpty();
    }

    @Test
    void shouldGetTeamById() {
        Team team = ORDINARY_CORAL_LYNX_TEAM();

        mockGetTeamById(mockServer, team.getId(), team);

        getTeamById(team.getId())
                .statusCode(HttpStatus.OK.value())
                .body("name", equalTo(team.getName()));
    }

    @Test
    void shouldThrowExceptionIfTeamIdDoesNotExist() {
        UUID teamId = UUID.randomUUID();

        mockGetTeamById(mockServer, teamId, null);

        getTeamById(teamId).validate(HttpStatus.NOT_FOUND.value(),"Team " + teamId +  " not found");
    }
}
