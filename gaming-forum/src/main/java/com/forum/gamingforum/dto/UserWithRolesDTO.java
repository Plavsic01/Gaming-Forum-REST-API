package com.forum.gamingforum.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserWithRolesDTO {
    private Long id;
    private String username;
    private String password;
    private Set<RoleDTO> roles = new HashSet<>();
}
