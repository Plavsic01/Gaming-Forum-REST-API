package com.forum.gamingforum.service.thread;

import com.forum.gamingforum.dto.ThreadDTO;
import com.forum.gamingforum.service.GenericService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ThreadServiceImpl implements GenericService<ThreadDTO> {
    @Override
    public List<ThreadDTO> findAll() {
        return null;
    }

    @Override
    public ThreadDTO findById(Long id) {
        return null;
    }

    @Override
    public ThreadDTO save(ThreadDTO entity) {
        return null;
    }

    @Override
    public ThreadDTO update(Long id, ThreadDTO entity) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
