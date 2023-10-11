package com.forum.gamingforumauth.controller;

import com.forum.gamingforumauth.controller.base.GenericController;
import com.forum.gamingforumauth.dto.UserDTO;
import com.forum.gamingforumauth.dto.UserWithRolesDTO;
import com.forum.gamingforumauth.service.user.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user/users")
public class UserController extends GenericController<UserDTO> {

    // Do this if I decide to have /register endpoint (think its better option to have /register endpoint)
    @Override
    public ResponseEntity<?> create(UserDTO entity, BindingResult result) {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/feign/findById/{id}")
    public ResponseEntity<?> feignClientFindById(@PathVariable Long id){
        UserDTO userDTO = ((UserServiceImpl)service).feignClientFindById(id);
        return new ResponseEntity<>(userDTO,HttpStatus.OK);
    }

    @GetMapping("/feign/findByUsername/{username}")
    public ResponseEntity<?> feignClientFindByUsername(@PathVariable String username){
        UserWithRolesDTO userDTO = ((UserServiceImpl)service).feignClientFindByUsername(username);
        return new ResponseEntity<>(userDTO,HttpStatus.OK);
    }


}
