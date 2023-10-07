package com.forum.gamingforum.service;

import jakarta.transaction.Transactional;

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
