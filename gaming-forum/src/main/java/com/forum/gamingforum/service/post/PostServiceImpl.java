package com.forum.gamingforum.service.post;

import com.forum.gamingforum.client.UserClient;
import com.forum.gamingforum.dto.PostDTO;
import com.forum.gamingforum.dto.UserDTO;
import com.forum.gamingforum.dto.UserWithRolesDTO;
import com.forum.gamingforum.exception.PostNotFoundException;
import com.forum.gamingforum.exception.ThreadNotFoundException;
import com.forum.gamingforum.model.Post;
import com.forum.gamingforum.model.Thread;
import com.forum.gamingforum.repository.PostRepository;
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

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements GenericService<PostDTO> {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private ThreadRepository threadRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserClient userClient;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @Override
    public Page<PostDTO> findAll(Pageable pageable) {
        Page<Post> postPage = postRepository.findAll(pageable);
        List<PostDTO> postDTOList = mapToThreadDTOList(postPage);
        return new PageImpl<>(postDTOList,pageable,postPage.getTotalElements());
    }
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @Override
    public Page<PostDTO> findAllByAuthorId(Long id, Pageable pageable) {
        Page<Post> postPage = postRepository.findAllByAuthor(id,pageable);
        List<PostDTO> postDTOList = mapToThreadDTOList(postPage);
        return new PageImpl<>(postDTOList,pageable,postPage.getTotalElements());
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public Page<PostDTO> findAllByThreadId(Long id,Pageable pageable){
        Page<Post> postPage = postRepository.findAllByThreadId(id,pageable);
        List<PostDTO> postDTOList = mapToThreadDTOList(postPage);
        return new PageImpl<>(postDTOList,pageable,postPage.getTotalElements());
    }
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @Override
    public PostDTO findById(Long id) {
        Post post = postRepository.findById(id).orElse(null);
        if(post == null){
            throw new PostNotFoundException();
        }
        UserDTO userDTO = userClient.findUserByAuthorId(post.getAuthor());
        post.setNumberOfViews(post.getNumberOfViews() + 1);
        postRepository.save(post);
        PostDTO postDTO = modelMapper.map(post,PostDTO.class);
        postDTO.setAuthor(userDTO);
        return postDTO;
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @Override
    public PostDTO save(PostDTO entity) {
        UserDetails userDetails = (UserDetails) userDetailsService
                .loadUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        Post post = modelMapper.map(entity,Post.class);
        Thread thread = threadRepository.findById(entity.getThread().getId()).orElse(null);
        if(thread == null){
            throw new ThreadNotFoundException();
        }

        post.setAuthor(userDetails.getId());
        post.setThread(thread);
        post.setNumberOfViews(0L);
        post = postRepository.save(post);
        return modelMapper.map(post,PostDTO.class);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @Override
    public PostDTO update(Long id, PostDTO entity) {
        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());

        Post post = postRepository.findById(id).orElse(null);
        if(post == null){
            throw new PostNotFoundException();
        }
        boolean hasAccess = isAuthor(id,SecurityContextHolder.getContext().getAuthentication().getName());
        if(!hasAccess){
            throw new AccessDeniedException("You are not authorized to access this resource.");
        }

        // We set id, thread and numberOfViews to null, because we don't want to change these values
        entity.setId(null);
        entity.setThread(null);
        entity.setNumberOfViews(null);
        modelMapper.map(entity,post);
        post = postRepository.save(post);
        return modelMapper.map(post,PostDTO.class);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @Override
    public void deleteById(Long id) {
        Post post = postRepository.findById(id).orElse(null);
        if(post == null){
            throw new PostNotFoundException();
        }
        boolean hasAccess = isAuthor(id,SecurityContextHolder.getContext().getAuthentication().getName());
        if(!hasAccess){
            throw new AccessDeniedException("You are not authorized to access this resource.");
        }
        postRepository.delete(post);
    }

    private List<PostDTO> mapToThreadDTOList(Page<Post> postPage){
        List<PostDTO> postDTOList = postPage
                .stream()
                .map(src -> {
                    UserDTO userDTO = userClient.findUserByAuthorId(src.getAuthor());
                    PostDTO postDTO = modelMapper.map(src,PostDTO.class);
                    postDTO.setAuthor(userDTO);
                    return postDTO;
                })
                .collect(Collectors.toList());
        return postDTOList;
    }

    private boolean isAuthor(Long postId,String loggedUsername){
        Post post = postRepository.findById(postId).get();
        UserWithRolesDTO loggedUser = userClient.findUserByUsername(loggedUsername);
        boolean isAdmin = loggedUser.getRoles()
                .stream()
                .anyMatch(grantedAuth -> grantedAuth.getName().equals("ROLE_ADMIN"));

        if(Objects.equals(post.getAuthor(), loggedUser.getId()) || isAdmin){
            return true;
        }
        return false;
    }
}
