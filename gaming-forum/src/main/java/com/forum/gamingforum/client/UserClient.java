package com.forum.gamingforum.client;

import com.forum.gamingforum.dto.UserDTO;
import com.forum.gamingforum.dto.UserWithRolesDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "auth-service",configuration = FeignClientConfig.class)
public interface UserClient {
    @GetMapping("/api/user/users/feign/findById/{authorId}")
    UserDTO findUserByAuthorId(@PathVariable Long authorId);

    @GetMapping("/api/user/users/feign/findByUsername/{username}")
    UserWithRolesDTO findUserByUsername(@PathVariable String username);


}
