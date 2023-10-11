package com.forum.gamingforum.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ThreadInPostDTO {
    @NotNull(message = "Thread Id must not be null")
    private Long id;
    private String title;
    private CategoryInThreadDTO category;
    private Long numberOfViews;
}
