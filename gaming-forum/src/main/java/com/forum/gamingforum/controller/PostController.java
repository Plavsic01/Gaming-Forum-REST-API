package com.forum.gamingforum.controller;

import com.forum.gamingforum.controller.base.GenericController;
import com.forum.gamingforum.dto.PostDTO;
import com.forum.gamingforum.service.post.PostServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/forum/posts")
public class PostController extends GenericController<PostDTO> {

    @GetMapping("/author-id/{id}")
    public ResponseEntity<Page<PostDTO>> findAllByAuthorId(@PathVariable(required = false) Long id, @PageableDefault(size = 20) Pageable pageable){
        Page<PostDTO> threadPage = service.findAllByAuthorId(id,pageable);
        return new ResponseEntity<Page<PostDTO>>(threadPage,HttpStatus.OK);
    }

    @GetMapping("/thread-id/{id}")
    public ResponseEntity<Page<PostDTO>> findById(@PathVariable Long id,@PageableDefault(size = 20) Pageable pageable){
        Page<PostDTO> entity = ((PostServiceImpl)service).findAllByThreadId(id,pageable);
        return new ResponseEntity<Page<PostDTO>>(entity, HttpStatus.OK);
    }

}
