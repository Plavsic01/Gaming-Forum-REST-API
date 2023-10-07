package com.forum.gamingforum.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ThreadDTO {
    private Long id;
    private String title;
    private List<PostDTO> posts = new ArrayList<>();
    private Long numberOfViews;
    private LocalDateTime dateAndTimeOfCreation;
}
