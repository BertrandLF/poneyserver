package com.ninja_squad.poneyserver.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * The controller which handle registration of new users
 * @author JB Nizet
 */
@RestController
@RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class RegistrationController {

    @Autowired
    private Database database;

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> register(@RequestBody User user) {
        for (User existing : database.getUsers()) {
            if (user.getLogin().equals(existing.getLogin())) {
                return new ResponseEntity<String>("This login is already in use", HttpStatus.BAD_REQUEST);
            }
        }
        database.addUser(user);
        return new ResponseEntity<String>(user.getLogin(), HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<User> list() {
        return database.getUsers();
    }
}