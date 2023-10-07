package com.forum.gamingforumauth.dto;

import com.forum.gamingforumauth.model.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserProfileDTO {
    private Long id;
    private UserDTO user;
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String address;
    private String phoneNumber;
    private Gender gender;

}
