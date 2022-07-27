package com.spring.angular.controller;

import com.spring.angular.exception.CustomErrorType;
import com.spring.angular.model.Users;
import com.spring.angular.repository.UsersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.DefaultMessageCodesResolver;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Validated
@RequestMapping("/api/user")
public class UsersController {

    public static final Logger LOGGER = LoggerFactory.getLogger(UsersController.class);

    private final UsersRepository usersRepository;

    public UsersController(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }


    @GetMapping("/")
    public ResponseEntity<List<Users>> listAllUsers() {
        List<Users> users = usersRepository.findAll();
        if(users.isEmpty())
        {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping(value = "/", consumes = "application/json")
    public ResponseEntity<Users> createUser(@RequestBody @Valid final Users users) {

        LOGGER.info("Creating User : {} ",users);

        if(usersRepository.findByName(users.getName()) != null)
        {
            LOGGER.error("Unable to create. A User with name {} already exist", users.getName());
            return new ResponseEntity<>(new CustomErrorType("Unable to create a new user. A User with name : "+
                    users.getName() + " already exist."), HttpStatus.CONFLICT);
        }
        usersRepository.save(users);
        return new ResponseEntity<>(users, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Users> getUserById(@PathVariable("id") final Long id) {
        Users user = usersRepository.findById(id).orElse(null);
        if ( user == null) {
            return new ResponseEntity<>(new CustomErrorType("User with id "+id+" not found!"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}", consumes = "application/json")
    public ResponseEntity<Users> updateUsers(@PathVariable("id") Long id, @RequestBody Users user) {
        Users currentUser = usersRepository.findById(id).orElse(null);
        if(currentUser == null)
        {
            return new ResponseEntity<>(new CustomErrorType("User with id "+id+" not found!"), HttpStatus.NOT_FOUND);
        }
        currentUser.setName(user.getName());
        currentUser.setAddress(user.getAddress());
        currentUser.setEmail(user.getEmail());
        usersRepository.saveAndFlush(currentUser);
        return new ResponseEntity<>(currentUser, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Users> deleteUser(@PathVariable("id") Long id) {
        Users users = usersRepository.findById(id).orElse(null);
        if(users == null)
        {
            return new ResponseEntity<>(new CustomErrorType("User with id "+id+" not found!"), HttpStatus.NOT_FOUND);
        }
        usersRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
