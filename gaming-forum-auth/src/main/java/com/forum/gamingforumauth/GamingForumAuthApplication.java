package com.forum.gamingforumauth;

import com.forum.gamingforumauth.model.Role;
import com.forum.gamingforumauth.model.User;
import com.forum.gamingforumauth.repository.RoleRepository;
import com.forum.gamingforumauth.repository.UserRepository;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

import java.util.ArrayList;

@SpringBootApplication
@EnableMethodSecurity(prePostEnabled = true,securedEnabled = true,jsr250Enabled = true)
public class GamingForumAuthApplication implements CommandLineRunner {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(GamingForumAuthApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		dodajKorisnika();
		dodajUloge();
	}

	public void dodajKorisnika(){
		User user = new User();
		user.setUsername("username");
		user.setEmail("email@email.com");
		user.setPassword("$2a$12$r44M1kPO3hGVN0AEJ2woi.HNmeI9o3K71kqfKyd47JjT5su5N2IVu");
		userRepository.save(user);
	}

	public void dodajUloge(){
		Role roleAdmin = new Role();
		roleAdmin.setName("ROLE_ADMIN");
		Role roleUser = new Role();
		roleUser.setName("ROLE_USER");
		roleRepository.save(roleAdmin);
		roleRepository.save(roleUser);
	}

}
