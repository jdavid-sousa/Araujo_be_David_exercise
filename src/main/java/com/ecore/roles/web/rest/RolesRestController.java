package com.ecore.roles.web.rest;

import com.ecore.roles.service.RolesService;
import com.ecore.roles.web.RolesApi;
import com.ecore.roles.web.dto.RoleDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1/roles")
public class RolesRestController implements RolesApi {

    private final RolesService rolesService;

    @Override
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RoleDto> createRole(
            @Valid @RequestBody RoleDto role) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(RoleDto.fromModel(rolesService.CreateRole(role.toModel())));
    }

    @Override
    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RoleDto>> getRoles() {
        List<RoleDto> roleDtoList = rolesService.GetRoles().stream()
                .map(RoleDto::fromModel)
                .collect(Collectors.toList());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(roleDtoList);
    }

    @Override
    @GetMapping(
            value = "/{roleId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RoleDto> getRole(
            @PathVariable UUID roleId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(RoleDto.fromModel(rolesService.GetRole(roleId)));
    }

    @Override
    @GetMapping(
            value = "/search",
            produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<RoleDto> search(@NotNull @RequestParam UUID teamMemberId, @NotNull @RequestParam UUID teamId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(RoleDto.fromModel(rolesService.search(teamMemberId, teamId)));
    }

}
