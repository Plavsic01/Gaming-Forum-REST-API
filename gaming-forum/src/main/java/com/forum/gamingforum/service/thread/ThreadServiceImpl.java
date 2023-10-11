package com.forum.gamingforum.service.thread;

import com.forum.gamingforum.client.UserClient;
import com.forum.gamingforum.dto.ThreadDTO;
import com.forum.gamingforum.dto.ThreadWithoutPostsDTO;
import com.forum.gamingforum.dto.UserDTO;
import com.forum.gamingforum.dto.UserWithRolesDTO;
import com.forum.gamingforum.exception.CategoryNotFoundException;
import com.forum.gamingforum.exception.ThreadNotFoundException;
import com.forum.gamingforum.model.Category;
import com.forum.gamingforum.model.Thread;
import com.forum.gamingforum.repository.CategoryRepository;
import com.forum.gamingforum.repository.ThreadRepository;
import com.forum.gamingforum.service.GenericService;
import com.forum.gamingforum.service.UserDetailsServiceImpl;
import com.forum.gamingforum.utils.UserDetails;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ThreadServiceImpl implements GenericService<ThreadDTO> {

    @Autowired
    private ThreadRepository threadRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserClient userClient;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @Override
    public Page<ThreadDTO> findAll(Pageable pageable) {
        Page<Thread> threadPage = threadRepository.findAll(pageable);
        List<ThreadDTO> threadDTOList = mapToThreadDTOList(threadPage);
        return new PageImpl<>(threadDTOList,pageable,threadPage.getTotalElements());
    }
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @Override
    public Page<ThreadDTO> findAllByAuthorId(Long id, Pageable pageable) {
        Page<Thread> threadPage = threadRepository.findAllByAuthor(id,pageable);
        List<ThreadDTO> threadDTOList = mapToThreadDTOList(threadPage);
        return new PageImpl<>(threadDTOList,pageable,threadPage.getTotalElements());
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public Page<ThreadWithoutPostsDTO> findAllByCategoryId(Long id, Pageable pageable) {
        Page<Thread> threadPage = threadRepository.findAllByCategoryId(id,pageable);
        List<ThreadWithoutPostsDTO> threadDTOList = threadPage
                .stream()
                .map(src -> {
                    UserDTO userDTO = userClient.findUserByAuthorId(src.getAuthor());
                    ThreadWithoutPostsDTO threadDTO = modelMapper.map(src,ThreadWithoutPostsDTO.class);
                    threadDTO.setAuthor(userDTO);
                    return threadDTO;
                })
                .collect(Collectors.toList());
        return new PageImpl<>(threadDTOList,pageable,threadPage.getTotalElements());
    }


    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @Transactional
    @Override
    public ThreadDTO findById(Long id) {
        Thread thread = threadRepository.findById(id).orElse(null);
        if(thread == null){
            throw new ThreadNotFoundException();
        }
        UserDTO userDTO = userClient.findUserByAuthorId(thread.getAuthor());
        thread.setNumberOfViews(thread.getNumberOfViews() + 1);
        threadRepository.save(thread);
        ThreadDTO threadDTO = modelMapper.map(thread,ThreadDTO.class);
        threadDTO.setAuthor(userDTO);
        return threadDTO;
    }
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @Override
    public ThreadDTO save(ThreadDTO entity) {
        UserDetails userDetails = (UserDetails) userDetailsService
                .loadUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        Category category = categoryRepository.findById(entity.getCategory().getId()).orElse(null);
        if(category == null){
            throw new CategoryNotFoundException();
        }
        Thread thread = modelMapper.map(entity,Thread.class);
        thread.setCategory(category);
        thread.setAuthor(userDetails.getId());
        thread.setNumberOfViews(0L);
        thread = threadRepository.save(thread);
        return modelMapper.map(thread,ThreadDTO.class);
    }
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @Override
    public ThreadDTO update(Long id, ThreadDTO entity) {
        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());

        Thread thread = threadRepository.findById(id).orElse(null);
        if(thread == null){
            throw new ThreadNotFoundException();
        }
        boolean hasAccess = isAuthor(id,SecurityContextHolder.getContext().getAuthentication().getName());
        if(!hasAccess){
            throw new AccessDeniedException("You are not authorized to access this resource.");
        }

        if(entity.getCategory() != null && entity.getCategory().getId() != null){
            Category category = categoryRepository.findById(entity.getCategory().getId()).orElse(null);
            if(category != null){
                thread.setCategory(category);
            }
        }
        entity.setId(null);
        entity.setCategory(null);
        entity.setNumberOfViews(null);
        modelMapper.map(entity,thread);
        threadRepository.save(thread);
        return modelMapper.map(thread,ThreadDTO.class);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @Override
    public void deleteById(Long id) {
        Thread thread = threadRepository.findById(id).orElse(null);
        if(thread == null){
            throw new ThreadNotFoundException();
        }
        boolean hasAccess = isAuthor(id,SecurityContextHolder.getContext().getAuthentication().getName());
        if(!hasAccess){
            throw new AccessDeniedException("You are not authorized to access this resource.");
        }

        threadRepository.delete(thread);
    }

    private List<ThreadDTO> mapToThreadDTOList(Page<Thread> threadPage){
        List<ThreadDTO> threadDTOList = threadPage
                .stream()
                .map(src -> {
                    UserDTO userDTO = userClient.findUserByAuthorId(src.getAuthor());
                    ThreadDTO threadDTO = modelMapper.map(src,ThreadDTO.class);
                    threadDTO.setAuthor(userDTO);
                    return threadDTO;
                })
                .collect(Collectors.toList());
        return threadDTOList;
    }

    private boolean isAuthor(Long threadId,String loggedUsername){
        Thread thread = threadRepository.findById(threadId).get();
        UserWithRolesDTO loggedUser = userClient.findUserByUsername(loggedUsername);
        boolean isAdmin = loggedUser.getRoles()
                .stream()
                .anyMatch(grantedAuth -> grantedAuth.getName().equals("ROLE_ADMIN"));

        if(Objects.equals(thread.getAuthor(), loggedUser.getId()) || isAdmin){
            return true;
        }
        return false;
    }

}
