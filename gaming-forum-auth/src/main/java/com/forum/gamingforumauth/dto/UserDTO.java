package com.forum.gamingforumauth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDTO {
    private Long id;
    @NotBlank(message = "Username can not be empty")
    private String username;
    @NotBlank(message = "E-mail address can not be empty")
    @Email(message = "Please enter valid e-mail address")
    private String email;
    @NotBlank(message = "Password can not be empty")
    @Size(min = 8,message = "Password must have at least 8 characters")
    private String password;
    private LocalDate registrationDate;
    private String profilePictureUrl;
}
