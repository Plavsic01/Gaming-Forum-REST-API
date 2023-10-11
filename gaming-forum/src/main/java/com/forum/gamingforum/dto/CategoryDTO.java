package com.forum.gamingforum.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CategoryDTO {
    private Long id;
    @NotBlank(message = "Category Name can not be empty")
    private String name;
//    private List<ThreadDTO> threads = new ArrayList<>();
}
