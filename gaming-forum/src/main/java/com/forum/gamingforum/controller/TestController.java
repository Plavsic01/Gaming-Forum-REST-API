package com.forum.gamingforum.controller;

import com.forum.gamingforum.client.UserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/forum")
public class TestController {
    @Autowired
    UserClient userClient;

    @GetMapping("/dobavi")
    public ResponseEntity<?> dobavi(){
        System.out.println(userClient.findUserByAuthorId(2L));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
