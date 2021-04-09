package com.thewhiterabbits.securityservice.controller;

import com.thewhiterabbits.securityservice.entity.AppUser;
import com.thewhiterabbits.securityservice.service.AccountService;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
public class UserController {
    Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private AccountService accountService;

    @PostMapping("/register")
    public AppUser register(@RequestBody UserForm userForm){
        log.error("enter to register controller");
        return accountService.saveUser(userForm.getUsername(), userForm.getPassword(), userForm.getConfirmedPassword());
    }

    @PostMapping("/login")
    public void login(@RequestBody UserForm userForm){
        log.error("enter to login controller.......................................");

        //return accountService.loadUserByUsername(userForm.getUsername());
    }
}


@Data
class UserForm{
    private String username;
    private String password;
    private String confirmedPassword;
}