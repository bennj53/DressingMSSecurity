package com.thewhiterabbits.securityservice.service;

import com.thewhiterabbits.securityservice.entity.AppRole;
import com.thewhiterabbits.securityservice.entity.AppUser;

import java.util.List;

public interface AccountService {
    AppUser saveUser(String username, String password, String confirmedPassword);
    AppRole save(AppRole role);
    AppUser loadUserByUsername(String username);
    void addRoleToUser(String username, String roleName);
    List<AppUser> findAll();
}
