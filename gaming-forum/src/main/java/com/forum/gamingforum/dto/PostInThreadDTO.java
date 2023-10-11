package com.forum.gamingforum.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PostInThreadDTO {
    private Long id;
    private String title;
    private String content;
    private UserDTO author;
    private Long numberOfViews;
    private LocalDateTime dateAndTimeOfCreation;
}
