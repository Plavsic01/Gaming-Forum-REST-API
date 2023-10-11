package com.forum.gamingforum.service;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GenericService<DTO> {
    Page<DTO> findAll(Pageable pageable);
    default Page<DTO> findAllByAuthorId(Long id,Pageable pageable){return null;}
    DTO findById(Long id);
    @Transactional
    DTO save(DTO entity);
    @Transactional
    DTO update(Long id,DTO entity);
    void deleteById(Long id);

}
