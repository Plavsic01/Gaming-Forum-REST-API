package com.forum.gamingforum.controller;

import com.forum.gamingforum.client.UserClient;
import com.forum.gamingforum.dto.UserDTO;
import com.forum.gamingforum.dto.UserWithRolesDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @Autowired
    private UserClient userClient;

    @GetMapping("/{username}")
    public ResponseEntity<UserWithRolesDTO> findByUsername(@PathVariable String username){
        UserWithRolesDTO entity = userClient.findUserByUsername(username);
        return new ResponseEntity<UserWithRolesDTO>(entity, HttpStatus.OK);
    }
}
