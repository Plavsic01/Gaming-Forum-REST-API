package com.forum.gamingforum;

import com.forum.gamingforum.model.Post;
import com.forum.gamingforum.model.Thread;
import com.forum.gamingforum.repository.PostRepository;
import com.forum.gamingforum.repository.ThreadRepository;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

import java.util.ArrayList;

@SpringBootApplication
@EnableFeignClients
@EnableMethodSecurity(prePostEnabled = true,securedEnabled = true,jsr250Enabled = true)
public class GamingForumApplication implements CommandLineRunner {

	/* KATEGORIJE SU ZANROVI IGRICA
	   THREADS SU IGRICE
	   POSTS SU OBJAVE VEZANE ZA ODREDJENU IGRICU
	   NPR:
	   KATEGORIJA -> FPS (FIRST PERSON SHOOTER)
	   THREAD     -> CALL OF DUTY WARZONE 2
	   POST       -> KOJE SU NAJBOLJE POSTAVKE U IGRICI?
	   TODO: COMMENT -> DODATI I KOMENTARE NA OBJAVE
	 */

	@Autowired
	ThreadRepository threadRepository;

	@Autowired
	PostRepository postRepository;

	public static void main(String[] args) {
		SpringApplication.run(GamingForumApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		dodaj();
		dodajPost();
	}

	public void dodaj(){
		ArrayList<Thread> threads = new ArrayList<>();
		for(int i = 0; i < 100;i++){
			Faker faker = new Faker();
			Thread t = new Thread();
			t.setTitle(faker.name().title());
			t.setAuthor(1L);
			t.setPosts(null);
			t.setNumberOfViews(0L);
			threads.add(t);
		}
		threadRepository.saveAll(threads);
	}

	public void dodajPost(){
//		Thread thread = threadRepository.findById(1L).get();
		ArrayList<Post> posts = new ArrayList<>();
		for(int i = 0; i < 500;i++){
			Faker faker = new Faker();
			Post p = new Post();
			p.setTitle(faker.name().title());
			p.setAuthor(1L);
			p.setContent(faker.book().title());
			p.setNumberOfViews(0L);
			posts.add(p);
		}
		postRepository.saveAll(posts);
	}
}
