package com.forum.gamingforum.repository;

import com.forum.gamingforum.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
    Page<Post> findAllByAuthor(Long author, Pageable pageable);
    Page<Post> findAllByThreadId(Long threadId, Pageable pageable);

}
