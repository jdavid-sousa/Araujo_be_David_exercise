package com.ecore.roles.api;

import com.ecore.roles.client.UsersClient;
import com.ecore.roles.client.model.User;
import com.ecore.roles.model.Role;
import com.ecore.roles.repository.RoleRepository;
import com.ecore.roles.service.impl.UsersServiceImpl;
import com.ecore.roles.utils.RestAssuredHelper;
import com.ecore.roles.web.dto.RoleDto;
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
import java.util.Optional;
import java.util.UUID;

import static com.ecore.roles.utils.MockUtils.*;
import static com.ecore.roles.utils.RestAssuredHelper.*;
import static com.ecore.roles.utils.TestData.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserApiTest {

    private final RestTemplate restTemplate;

    @InjectMocks
    private UsersServiceImpl usersService;

    @Mock
    private UsersClient usersClient;

    private MockRestServiceServer mockServer;

    @LocalServerPort
    private int port;

    @Autowired
    public UserApiTest(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @BeforeEach
    void setUp() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
        RestAssuredHelper.setUp(port);
    }

    @Test
    void shouldGetUsers() {
        List<User> userList = new ArrayList<>();
        userList.add(GIANNI_USER());

        mockGetUsers(mockServer, userList);
        UserDto[] users = getUsers()
                .extract().as(UserDto[].class);

        assertThat(users).isNotEmpty();
    }

    @Test
    void shouldGetUserById() {
        User user = GIANNI_USER();

        mockGetUserById(mockServer, user.getId(), user);

       getUserById(user.getId())
               .statusCode(HttpStatus.OK.value())
               .body("displayName", equalTo(user.getDisplayName()));
    }

    @Test
    void shouldThrowExceptionIfUserIdDoesNotExist() {
        UUID userId = UUID.randomUUID();

        mockGetUserById(mockServer, userId, null);

        getUserById(userId).validate(HttpStatus.NOT_FOUND.value(),"User " + userId +  " not found");
    }
}
