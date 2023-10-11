package com.forum.gamingforum.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ThreadDTO {
    private Long id;
    @NotBlank(message = "Title can not be empty")
    private String title;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UserDTO author;
//    private List<PostInThreadDTO> posts = new ArrayList<>();
    @Valid
    private CategoryInThreadDTO category;
    private Long numberOfViews;
    private LocalDateTime dateAndTimeOfCreation;
}
