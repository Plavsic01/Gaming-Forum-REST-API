package com.forum.gamingforumauth.service.user;

import com.forum.gamingforumauth.model.Role;
import com.forum.gamingforumauth.model.User;
import com.forum.gamingforumauth.repository.RoleRepository;
import com.forum.gamingforumauth.repository.UserRepository;
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
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElse(null);
        if(user != null){
            Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
            for(Role role: user.getRoles()){
                grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
            }
            return new com.forum.gamingforumauth.utils.UserDetails(user.getId(),user.getUsername(),
                    user.getPassword(),grantedAuthorities);
        }
        return null;
    }
}
