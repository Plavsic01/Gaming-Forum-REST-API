package com.forum.gamingforumauth.service.user;

import com.forum.gamingforumauth.dto.PasswordDTO;
import com.forum.gamingforumauth.dto.UserDTO;
import com.forum.gamingforumauth.dto.UserWithRolesDTO;
import com.forum.gamingforumauth.exception.PasswordDoesNotMatchException;
import com.forum.gamingforumauth.exception.UserNotFoundException;
import com.forum.gamingforumauth.model.Role;
import com.forum.gamingforumauth.model.User;
import com.forum.gamingforumauth.model.UserProfile;
import com.forum.gamingforumauth.repository.RoleRepository;
import com.forum.gamingforumauth.repository.UserRepository;
import com.forum.gamingforumauth.service.GenericService;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements GenericService<UserDTO> {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public List<UserDTO> findAll() {
        List<User> users = (List<User>) userRepository.findAll();
        List<UserDTO> userDTOList = users.stream()
                .map(usr -> {
                    usr.setPassword(null);
                    return modelMapper.map(usr,UserDTO.class);
                })
                .collect(Collectors.toList());
        return userDTOList;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public UserDTO findById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if(user == null){
            throw new UserNotFoundException();
        }
        user.setPassword(null);
        return modelMapper.map(user,UserDTO.class);
    }
    @Override
    public UserDTO save(UserDTO entity) {
        Role role = roleRepository.findByName("ROLE_USER").orElse(null);
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        User user = modelMapper.map(entity,User.class);
        user.setRoles(Collections.singleton(role));
        UserProfile userProfile = new UserProfile();
        userProfile.setUser(user);
        user.setUserProfile(userProfile);
        userRepository.save(user);
        user.setPassword(null);
        return modelMapper.map(user,UserDTO.class);
    }

    /* When user changes his username, on frontend we need to log out user and let him log in again
       because in JSON WEB TOKEN there will be still old username
    */
    @PreAuthorize("hasAnyRole('ADMIN', 'USER') and (hasRole('ADMIN') or #id == authentication.principal.id)")
    @Override
    public UserDTO update(Long id, UserDTO entity) {
        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        User user = userRepository.findById(id).orElse(null);
        if(user == null){
            throw new UserNotFoundException();
        }
        // Set ID to null, because end user can put in requestBody and that causes an Error
        entity.setId(null);
        /* Set password to null, because we don't want to change password here
           We will have special endpoint and service method to change password
        */
        entity.setPassword(null);
        modelMapper.map(entity,user);
        user = userRepository.save(user);
        user.setPassword(null);
        return modelMapper.map(user,UserDTO.class);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public void deleteById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if(user == null){
            throw new UserNotFoundException();
        }
        userRepository.delete(user);
    }


    @PreAuthorize("hasAnyRole('ADMIN', 'USER') and (hasRole('ADMIN') or #id == authentication.principal.id)")
    public void changePassword(Long id,PasswordDTO entity){
        User currUser = userRepository.findById(id).orElse(null);
        if(currUser == null){
            throw new UserNotFoundException();
        }

        if(!passwordEncoder.matches(entity.getCurrentPassword(), currUser.getPassword())){
            throw new PasswordDoesNotMatchException();
        }

        String newEncryptedPassword = passwordEncoder.encode(entity.getNewPassword());
        currUser.setPassword(newEncryptedPassword);
        userRepository.save(currUser);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public UserDTO feignClientFindById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if(user == null){
            throw new UserNotFoundException();
        }
        user.setPassword(null);
        user.setEmail(null);
        return modelMapper.map(user,UserDTO.class);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER') and (hasRole('ADMIN') or #username == authentication.principal.username)")
    public UserWithRolesDTO feignClientFindByUsername(String username) {
        User user = userRepository.findByUsername(username).orElse(null);
        if(user == null){
            throw new UserNotFoundException();
        }
        UserWithRolesDTO s = modelMapper.map(user,UserWithRolesDTO.class);
        return modelMapper.map(user,UserWithRolesDTO.class);
    }

}
