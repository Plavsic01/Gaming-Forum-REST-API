package com.forum.gamingforum.service.category;

import com.forum.gamingforum.dto.CategoryDTO;
import com.forum.gamingforum.exception.CategoryNotFoundException;
import com.forum.gamingforum.model.Category;
import com.forum.gamingforum.repository.CategoryRepository;
import com.forum.gamingforum.service.GenericService;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements GenericService<CategoryDTO> {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper modelMapper;

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @Override
    public Page<CategoryDTO> findAll(Pageable pageable) {
        Page<Category> categoryPage = categoryRepository.findAll(pageable);
        List<CategoryDTO> categoryDTOList = mapToCategoryDTOList(categoryPage);
        return new PageImpl<>(categoryDTOList,pageable,categoryPage.getTotalElements());
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @Override
    public CategoryDTO findById(Long id) {
        Category category = categoryRepository.findById(id).orElse(null);
        if(category == null){
            throw new CategoryNotFoundException();
        }
        return modelMapper.map(category,CategoryDTO.class);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public CategoryDTO save(CategoryDTO entity) {
        Category category = modelMapper.map(entity,Category.class);
        category.setThreads(null);
        categoryRepository.save(category);
        return modelMapper.map(category,CategoryDTO.class);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public CategoryDTO update(Long id, CategoryDTO entity) {
        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        Category category = categoryRepository.findById(id).orElse(null);
        if(category == null){
            throw new CategoryNotFoundException();
        }
        entity.setId(null);
        modelMapper.map(entity,category);
        category = categoryRepository.save(category);
        return modelMapper.map(category,CategoryDTO.class);

    }
    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public void deleteById(Long id) {
        Category category = categoryRepository.findById(id).orElse(null);
        if(category == null){
            throw new CategoryNotFoundException();
        }
        categoryRepository.delete(category);
    }

    private List<CategoryDTO> mapToCategoryDTOList(Page<Category> categoryPage){
        List<CategoryDTO> postDTOList = categoryPage
                .stream()
                .map(src ->  modelMapper.map(src,CategoryDTO.class))
                .collect(Collectors.toList());
        return postDTOList;
    }

}
