package com.thewhiterabbits.securityservice;

import com.thewhiterabbits.securityservice.entity.AppRole;
import com.thewhiterabbits.securityservice.service.AccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.stream.Stream;

@SpringBootApplication
public class SecurityServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecurityServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner start(AccountService accountService){
		return args -> {
			accountService.save(new AppRole(null,"USER"));
			accountService.save(new AppRole(null, "ADMIN"));
			Stream.of("user1", "user2", "user3", "bennj53","bennjUser").forEach(u->accountService.saveUser(u, "1234", "1234"));
			accountService.addRoleToUser("bennj53", "ADMIN");
		};
	}

	@Bean
	BCryptPasswordEncoder getBCPE(){
		return new BCryptPasswordEncoder();
	}


}
