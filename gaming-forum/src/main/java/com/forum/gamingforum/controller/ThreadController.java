package com.forum.gamingforum.controller;

import com.forum.gamingforum.controller.base.GenericController;
import com.forum.gamingforum.dto.ThreadDTO;
import com.forum.gamingforum.dto.ThreadWithoutPostsDTO;
import com.forum.gamingforum.service.thread.ThreadServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/forum/threads")
public class ThreadController extends GenericController<ThreadDTO> {

    @GetMapping("/category-id/{id}")
    public ResponseEntity<Page<ThreadWithoutPostsDTO>> findById(@PathVariable Long id, @PageableDefault(size = 20) Pageable pageable){
        Page<ThreadWithoutPostsDTO> entity = ((ThreadServiceImpl)service).findAllByCategoryId(id,pageable);
        return new ResponseEntity<Page<ThreadWithoutPostsDTO>>(entity, HttpStatus.OK);
    }

    @GetMapping("/author-id/{id}")
    public ResponseEntity<Page<ThreadDTO>> findAllByAuthorId(@PathVariable(required = false) Long id,@PageableDefault(size = 20) Pageable pageable){
        Page<ThreadDTO> threadPage = service.findAllByAuthorId(id,pageable);
        return new ResponseEntity<Page<ThreadDTO>>(threadPage,HttpStatus.OK);
    }


}
