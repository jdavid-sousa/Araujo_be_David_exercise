package com.ecore.roles.service.impl;

import com.ecore.roles.client.UsersClient;
import com.ecore.roles.client.model.User;
import com.ecore.roles.exception.ResourceNotFoundException;
import com.ecore.roles.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UsersServiceImpl implements UsersService {

    private final UsersClient usersClient;

    @Autowired
    public UsersServiceImpl(UsersClient usersClient) {
        this.usersClient = usersClient;
    }

    @Override
    public User getUser(UUID id) {
        ResponseEntity<User> user = usersClient.getUser(id);

        if((user.getStatusCode() != HttpStatus.OK) || (user.getBody() == null)) {
            throw new ResourceNotFoundException(User.class, id);
        }
        return user.getBody();
    }

    @Override
    public List<User> getUsers() {
        return usersClient.getUsers().getBody();
    }
}
