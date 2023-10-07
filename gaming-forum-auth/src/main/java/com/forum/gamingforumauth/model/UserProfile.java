package com.forum.gamingforumauth.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_profile")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(fetch = FetchType.LAZY)
    private User user;
    private String firstName;
    private String lastName;
    @Column
    private String dateOfBirth;
    @Column
    private String address;
    @Column
    private String phoneNumber;
    @Enumerated(EnumType.STRING)
    private Gender gender;


}
