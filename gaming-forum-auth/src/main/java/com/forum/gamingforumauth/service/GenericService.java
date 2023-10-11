package com.forum.gamingforumauth.service;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface GenericService<DTO> {
    List<DTO> findAll();
    DTO findById(Long id);
    @Transactional
    DTO save(DTO entity);
    @Transactional
    DTO update(Long id,DTO entity);
    void deleteById(Long id);

}
