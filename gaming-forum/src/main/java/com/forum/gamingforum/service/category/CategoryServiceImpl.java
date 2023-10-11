package com.forum.gamingforum.service.category;

import com.forum.gamingforum.dto.CategoryDTO;
import com.forum.gamingforum.service.GenericService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements GenericService<CategoryDTO> {
    @Override
    public Page<CategoryDTO> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public CategoryDTO findById(Long id) {
        return null;
    }

    @Override
    public CategoryDTO save(CategoryDTO entity) {
        return null;
    }

    @Override
    public CategoryDTO update(Long id, CategoryDTO entity) {
        return null;
    }

    @Override
    public void deleteById(Long id) {}

}
