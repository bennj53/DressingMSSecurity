package com.thewhiterabbits.securityservice.service;

import com.thewhiterabbits.securityservice.dao.AppRoleRepository;
import com.thewhiterabbits.securityservice.dao.AppUserRepository;
import com.thewhiterabbits.securityservice.entity.AppRole;
import com.thewhiterabbits.securityservice.entity.AppUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class AccountServiceImpl implements AccountService{
    Logger log = LoggerFactory.getLogger(AccountServiceImpl.class);

    //injection via constructeur
    private AppUserRepository appUserRepository;
    private AppRoleRepository appRoleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    //pour injection via constructeur
    public AccountServiceImpl(AppUserRepository appUserRepository, AppRoleRepository appRoleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.appUserRepository = appUserRepository;
        this.appRoleRepository = appRoleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public AppUser saveUser(String username, String password, String confirmedPassword) {
        log.error("start save user from AccountServiceImpl");
        AppUser user=appUserRepository.findByUsername(username);
        if(user!=null) throw new RuntimeException("User already exists");
        if(!password.equals(confirmedPassword)) throw new RuntimeException("Please confirm your password");
        AppUser appUser = new AppUser();
        appUser.setUsername(username);
        appUser.setActived(true);
        //crypter le password
        appUser.setPassword(bCryptPasswordEncoder.encode(password));
        appUserRepository.save(appUser);
        addRoleToUser(username, "USER");

        return appUser;
    }

    @Override
    public AppRole save(AppRole role) {
        return appRoleRepository.save(role);
    }

    @Override
    public AppUser loadUserByUsername(String username) {
        return appUserRepository.findByUsername(username);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        log.error("start addRoleToUser from AccountServiceImpl");
        AppUser appUser = appUserRepository.findByUsername(username);
        AppRole appRole = appRoleRepository.findByRoleName(roleName);
        appUser.getRoles().add(appRole);
        //pas besoin de save car @Transactional, la collection change donc save auto
    }

    @Override
    public List<AppUser> findAll() {
        return appUserRepository.findAll();
    }
}
