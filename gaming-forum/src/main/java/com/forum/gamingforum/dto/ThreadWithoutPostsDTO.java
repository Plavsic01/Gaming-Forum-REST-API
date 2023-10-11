package com.forum.gamingforum.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ThreadWithoutPostsDTO {
    private Long id;
    private String title;
    private UserDTO author;
    private CategoryInThreadDTO category;
    private Long numberOfViews;
    private LocalDateTime dateAndTimeOfCreation;
}
