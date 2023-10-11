package com.forum.gamingforum.repository;


import com.forum.gamingforum.model.Thread;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThreadRepository extends JpaRepository<Thread,Long> {
    Page<Thread> findAllByAuthor(Long author, Pageable pageable);

    Page<Thread> findAllByCategoryId(Long categoryId, Pageable pageable);

}
