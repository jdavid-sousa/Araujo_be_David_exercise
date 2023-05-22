package com.ecore.roles.web.rest;

import com.ecore.roles.model.Membership;
import com.ecore.roles.service.MembershipsService;
import com.ecore.roles.web.MembershipsApi;
import com.ecore.roles.web.dto.MembershipDto;
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
@RequestMapping(value = "/v1/roles/memberships")
public class MembershipsRestController implements MembershipsApi {

    private final MembershipsService membershipsService;

    @Override
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE ,
            produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<MembershipDto> assignRoleToMembership(
            @Valid @RequestBody(required = true) MembershipDto membershipDto) {
        Membership membership = membershipsService.assignRoleToMembership(membershipDto.toModel());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(MembershipDto.fromModel(membership));
    }

    @Override
    @GetMapping(
            value = "/search",
            produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<List<MembershipDto>> getMemberships(
           @NotNull @RequestParam UUID roleId) {
        List<MembershipDto> newMembershipDto = membershipsService.getMemberships(roleId).stream()
                .map(MembershipDto::fromModel)
                .collect(Collectors.toList());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(newMembershipDto);
    }

}
