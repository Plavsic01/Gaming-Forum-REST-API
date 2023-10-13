package com.forum.gamingforumauth.controller;

import com.forum.gamingforumauth.dto.LoginDTO;
import com.forum.gamingforumauth.dto.PasswordDTO;
import com.forum.gamingforumauth.dto.UserDTO;
import com.forum.gamingforumauth.service.GenericService;
import com.forum.gamingforumauth.service.user.UserServiceImpl;
import com.forum.gamingforumauth.utils.JwtTokenUtilsImpl;
import com.forum.gamingforumauth.utils.TokenUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/user")
public class AuthController {

    @Autowired
    private TokenUtils tokenUtils;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private GenericService<UserDTO> userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserDTO user, BindingResult result) {
        if(result.hasErrors()){
            return new ResponseEntity<>(errorMessages(result.getFieldErrors()),HttpStatus.BAD_REQUEST);
        }
        userService.save(user);
        return new ResponseEntity<>("User Registered Successfully",HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody LoginDTO login){
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login.getUsername(),login.getPassword()));
        UserDetails userDetails = userDetailsService.loadUserByUsername(login.getUsername());
        String token = tokenUtils.generateToken(userDetails);
        return new ResponseEntity<>(token,HttpStatus.OK);
    }

    @PutMapping("/change-password/{id}")
    public ResponseEntity<?> updatePassword(@PathVariable Long id,@Valid @RequestBody PasswordDTO entity,
                                            BindingResult result) {
        if(result.hasErrors()){
            return new ResponseEntity<>(errorMessages(result.getFieldErrors()),HttpStatus.BAD_REQUEST);
        }
        UserServiceImpl userServiceImpl = (UserServiceImpl) userService;
        userServiceImpl.changePassword(id,entity);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private List<String> errorMessages(List<FieldError> errors){
        List<String> errorMessages = new ArrayList<>();
        for(FieldError error:errors){
            errorMessages.add(error.getDefaultMessage());
        }
        return errorMessages;
    }
}
