package com.forum.gamingforum.service;

import com.forum.gamingforum.client.UserClient;
import com.forum.gamingforum.dto.RoleDTO;
import com.forum.gamingforum.dto.UserWithRolesDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserClient userClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserWithRolesDTO user = userClient.findUserByUsername(username);
        if(user != null){
            Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
            for(RoleDTO role: user.getRoles()){
                grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
            }
            return new com.forum.gamingforum.utils.
                    UserDetails(user.getId(),user.getUsername(),user.getPassword(),grantedAuthorities);
        }
        return null;
    }
}
