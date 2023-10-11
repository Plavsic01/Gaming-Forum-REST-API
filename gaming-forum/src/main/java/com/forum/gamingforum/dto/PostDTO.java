package com.forum.gamingforum.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PostDTO {
    private Long id;
    @NotBlank(message = "Title can not be empty")
    private String title;
    @NotBlank(message = "Content can not be empty")
    private String content;
    private UserDTO author;
    @Valid
    @NotNull(message = "Thread can not be empty")
    private ThreadInPostDTO thread;
    private Long numberOfViews;
    private LocalDateTime dateAndTimeOfCreation;

}
