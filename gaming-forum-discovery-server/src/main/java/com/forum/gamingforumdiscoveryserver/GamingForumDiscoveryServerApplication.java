package com.forum.gamingforumdiscoveryserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class GamingForumDiscoveryServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(GamingForumDiscoveryServerApplication.class, args);
	}

}
