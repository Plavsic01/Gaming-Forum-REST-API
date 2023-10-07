package com.forum.gamingforum.service.post;

import com.forum.gamingforum.dto.PostDTO;
import com.forum.gamingforum.service.GenericService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PostServiceImpl implements GenericService<PostDTO> {
    @Override
    public List<PostDTO> findAll() {
        return null;
    }

    @Override
    public PostDTO findById(Long id) {
        return null;
    }

    @Override
    public PostDTO save(PostDTO entity) {
        return null;
    }

    @Override
    public PostDTO update(Long id, PostDTO entity) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
