package com.ecore.roles.web.rest;

import com.ecore.roles.service.UsersService;
import com.ecore.roles.web.UsersApi;
import com.ecore.roles.web.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1/users")
public class UsersRestController implements UsersApi {

    private final UsersService usersService;

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserDto>> getUsers() {
        List<UserDto> userDtos = usersService.getUsers().stream()
                .map(UserDto::fromModel)
                .collect(Collectors.toList());
        return ResponseEntity.ok(userDtos);
    }

    @Override
    @GetMapping(
            value = "/{userId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> getUser(
            @PathVariable UUID userId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(UserDto.fromModel(usersService.getUser(userId)));
    }
}
