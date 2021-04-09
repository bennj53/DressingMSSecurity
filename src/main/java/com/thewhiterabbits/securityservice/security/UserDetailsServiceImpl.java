package com.thewhiterabbits.securityservice.security;

import com.thewhiterabbits.securityservice.entity.AppUser;
import com.thewhiterabbits.securityservice.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    AccountService accountService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.error("enter in UserDetailsServiceImpl");
        AppUser appUser = accountService.loadUserByUsername(username);

        if(appUser==null) throw new UsernameNotFoundException("user invalid");
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        appUser.getRoles().forEach(role->{
            authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
        });
        //obj User de spring pr verif mdp saisie en l encodant = mdp en base deja encode
        return new User(appUser.getUsername(), appUser.getPassword(), authorities);
    }
}
