package com.forum.gamingforumauth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PasswordDTO {
    @NotBlank(message = "Password can not be empty")
    private String currentPassword;
    @NotBlank(message = "Password can not be empty")
    @Size(min = 8,message = "Password must have at least 8 characters")
    private String newPassword;

}
