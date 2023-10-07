package com.forum.gamingforum;

import com.forum.gamingforum.model.Thread;
import com.forum.gamingforum.repository.ThreadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class GamingForumApplication implements CommandLineRunner {

	@Autowired
	ThreadRepository threadRepository;

	public static void main(String[] args) {
		SpringApplication.run(GamingForumApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		dodaj();
	}

	public void dodaj(){
		Thread t = new Thread();
		t.setTitle("Naslov");
		t.setAuthorId(1L);
		t.setPosts(null);
		t.setNumberOfViews(1L);
		threadRepository.save(t);
	}
}
