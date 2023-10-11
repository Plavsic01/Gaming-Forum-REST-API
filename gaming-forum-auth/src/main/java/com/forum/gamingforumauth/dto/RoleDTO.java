package com.forum.gamingforumauth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RoleDTO {
    private Long id;
    @NotBlank(message = "Role name can not be empty")
    private String name;

}
