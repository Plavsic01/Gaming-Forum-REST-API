package com.forum.gamingforum.controller;

import com.forum.gamingforum.controller.base.GenericController;
import com.forum.gamingforum.dto.CategoryDTO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/categories")
public class CategoryController extends GenericController<CategoryDTO> {

}
